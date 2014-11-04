package org.oi.twitter.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class FollowingDao {
    private @Autowired JdbcTemplate jdbcTemplate;
    private static final String SQL_ADD_FOLLOWING_RELATION = "insert into FOLLOWING_RELATION(follower_id, followed_id) values(?, ?)";
    private static final String SQL_REMOVE_FOLLOWING_RELATION = "delete from FOLLOWING_RELATION where follower_id = ? and followed_id = ?";
    private static final String SQL_IS_FOLLOWING_RELATION_PRESENT = "select count(1) relation_count from FOLLOWING_RELATION where follower_id = ? and followed_id = ?";
    
    public boolean isFollowed(Long followerId, Long followedId) {
        return jdbcTemplate.queryForObject(SQL_IS_FOLLOWING_RELATION_PRESENT, Boolean.class, followerId, followedId);
    }
    
    public void follow(Long followerId, Long followedId) {
        jdbcTemplate.update(SQL_ADD_FOLLOWING_RELATION, followerId, followedId);
    }
    
    public void unfollow(Long followerId, Long followedId) {
        jdbcTemplate.update(SQL_REMOVE_FOLLOWING_RELATION, followerId, followedId);
    }
    
}
