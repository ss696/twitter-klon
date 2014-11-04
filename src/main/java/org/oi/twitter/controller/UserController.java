package org.oi.twitter.controller;

import java.io.File;
import java.io.IOException;
import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.oi.twitter.domain.User;
import org.oi.twitter.domain.ui.UserInfo;
import org.oi.twitter.exceptions.ImageUploadException;
import org.oi.twitter.service.FollowingService;
import org.oi.twitter.service.TweetService;
import org.oi.twitter.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/user")
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private @Autowired UserService userService;
    private @Autowired TweetService tweetService;
    private @Autowired FollowingService followingService;
    
    @RequestMapping(value="/new", method=RequestMethod.GET)
    public String createUser(Model model) {
        model.addAttribute(new User());
        
        return "editUser";
    }
    
    @RequestMapping(value="/all", method=RequestMethod.GET)
    public String listAllUsers(Model model, Principal principal) {
        model.addAttribute("userList", userService.fetchUserListForUser(principal.getName()));
        return "users";
    }
    
    @RequestMapping(value="/new", method=RequestMethod.POST)
    public String addUserFromForm(User user, BindingResult bindingResult, @RequestParam(value="image", required=false) MultipartFile image, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return "editUser";
        }

        if (userService.checkIfUsernameIsAvailable(user.getNickname())) {
            bindingResult.reject(null, "We already have a user with such nickname, pick another one, please");
            return "editUser";
        }
        
        user.setUserPic("default.jpg");
        userService.saveUser(user);
        
        try {
            if (!image.isEmpty()) {
                validateImage(image);
                user.setUserPic(user.getNickname() + ".jpg");
                String imagePath = request.getSession().getServletContext().getRealPath("/resources/userpics/")+File.separator + user.getUserPic();               
                saveImage(imagePath, image);
                userService.updateUser(user);
            }
        } catch(Exception iue) {
            log.error(iue.getMessage(), iue);
            bindingResult.reject(null, iue.getMessage());
            userService.deleteUser(user);
            return "editUser";
        }
        
        return "redirect:/welcome/";
    }
    
    @RequestMapping(value="/profile/{username}", method=RequestMethod.GET)
    public String showUserProfile(@PathVariable String username, Model model, Principal principal) {
        UserInfo user = userService.fetchUserInfo(username, principal.getName());
        model.addAttribute("user", user);
        model.addAttribute("tweets", tweetService.fetchUserTweets(user.getId()));
        return "showUser";
    }
    
    @RequestMapping(value="/follow", method=RequestMethod.POST)
    public String followUser(@RequestParam("userToFollow") String nameOfUserToFollow, HttpServletRequest request, Principal principal) {
        User followedUser = userService.fetchUser(nameOfUserToFollow);
        User followingUser = userService.fetchUser(principal.getName());
        
        followingService.follow(followingUser.getId(), followedUser.getId());
        
        return "redirect:"+ request.getHeader("Referer");
    }
    
    @RequestMapping(value="/unfollow", method=RequestMethod.POST)
    public String unfollowUser(@RequestParam("userToUnfollow") String nameOfUserToFollow, HttpServletRequest request, Principal principal) {
        User followedUser = userService.fetchUser(nameOfUserToFollow);
        User followingUser = userService.fetchUser(principal.getName());
        
        followingService.unfollow(followingUser.getId(), followedUser.getId());
        
        return "redirect:"+ request.getHeader("Referer");
    }

    private void saveImage(String fileName, MultipartFile image) throws ImageUploadException {
        try {
            File file = new File(fileName);
            FileUtils.writeByteArrayToFile(file, image.getBytes());
        } catch(IOException ioe) {
            log.error(ioe.getMessage(), ioe);
            throw new ImageUploadException("Unable to save image", ioe);
        }
    }
    
    private void validateImage(MultipartFile image) {
        if (!image.getContentType().equals("image/jpeg")) {
            throw new ImageUploadException("Only JPG images accepted");
        }
    }
}
