package org.oi.twitter.controller;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.oi.twitter.domain.User;
import org.oi.twitter.service.TweetService;
import org.oi.twitter.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/tweet")
public class TweetController {
    private static final Logger log = LoggerFactory.getLogger(TweetController.class);
    private @Autowired TweetService tweetService;
    private @Autowired UserService userService;
    
    @RequestMapping(method=RequestMethod.POST)
    public String addTweet(@RequestParam("text") String tweetText, Principal principal, HttpServletRequest request) {
        log.info("addTweet called with params: text={}, username={}", tweetText, principal.getName());
        User user = userService.fetchUser(principal.getName());
        tweetService.saveUserTweet(user, tweetText);
        return "redirect:"+ request.getHeader("Referer");
    }
    
    @RequestMapping(value="/timeline", method=RequestMethod.GET)
    public String getTweetListForUser(Model model, Principal principal) {
        model.addAttribute("tweets", tweetService.fetchUserTimeLineTweets(principal.getName()));
        return "tweetTimeLine";
    }

}
