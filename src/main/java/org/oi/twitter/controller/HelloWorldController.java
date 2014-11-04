package org.oi.twitter.controller;

import org.oi.twitter.service.TweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping({"/welcome", "/"})
public class HelloWorldController {
    private static final int TWEET_LIMIT = 25;
    private @Autowired TweetService tweetService;
    
    @RequestMapping(method = RequestMethod.GET)
    public String printWelcome(ModelMap model) {
        model.addAttribute("tweets", tweetService.fetchRecentTweets(TWEET_LIMIT));
        return "hello";
    }
    
}
