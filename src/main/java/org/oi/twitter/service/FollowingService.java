package org.oi.twitter.service;

import org.oi.twitter.dao.FollowingDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FollowingService {
    @Autowired private FollowingDao followingDao;
    
    public void follow(Long followerId, Long followedId) {
        followingDao.follow(followerId, followedId);
    }
    
    public void unfollow(Long followerId, Long followedId) {
        followingDao.unfollow(followerId, followedId);
    }
    
    public void isFollowed(Long followerId, Long followedId) {
        followingDao.isFollowed(followerId, followedId);
    }
}
