package org.oi.twitter.service;

import java.util.List;

import org.joda.time.DateTime;
import org.oi.twitter.dao.TweetDao;
import org.oi.twitter.domain.User;
import org.oi.twitter.domain.ui.TweetInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TweetService {
    private @Autowired TweetDao tweetDao;
    
    public List<TweetInfo> fetchRecentTweets(int limit) {
        return tweetDao.fetchRecentTweets(limit);
    }
    
    public List<TweetInfo> fetchUserTweets(Long userId) {
        return tweetDao.fetchUserTweets(userId);
    }
    
    public void saveUserTweet(User user, String tweetText) {
        tweetDao.saveUserTweet(user.getId(), tweetText, DateTime.now());
    }
    
    public List<TweetInfo> fetchUserTimeLineTweets(String username) {
        return tweetDao.fetchUserTimeLineTweets(username);
    }
}
