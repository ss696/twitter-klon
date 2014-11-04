package org.oi.twitter.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.joda.time.DateTime;
import org.oi.twitter.domain.ui.TweetInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class TweetDao {
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert jdbcInsert;
    private TweetInfoMapper tweetInfoMapper = new TweetInfoMapper();
    private static final Logger log = LoggerFactory.getLogger(TweetDao.class);
    private static final String SQL_FETCH_RECENT_TWEETS = "SELECT T.ID ID, T.USER_ID USER_ID, U.NICKNAME NICKNAME, U.USER_PIC USER_PIC, T.TWEET_DATE TWEET_DATE, T.TEXT TEXT" + 
                                                            " FROM TWEET T INNER JOIN `USER` U" +
                                                            " ON T.USER_ID = U.ID" +
                                                            " ORDER BY TWEET_DATE DESC" +
                                                            " LIMIT ?";
    private static final String SQL_FETCH_USER_TWEETS = "SELECT T.ID ID, T.USER_ID USER_ID, U.NICKNAME NICKNAME, U.USER_PIC USER_PIC, T.TWEET_DATE TWEET_DATE, T.TEXT TEXT" + 
                                                        " FROM TWEET T INNER JOIN `USER` U" +
                                                        " ON T.USER_ID = U.ID" +
                                                        " WHERE U.ID = ?" +
                                                        " ORDER BY TWEET_DATE DESC";
    private static final String SQL_FETCH_TIMELINE_TWEETS = "SELECT T.ID ID, T.USER_ID USER_ID, U.NICKNAME NICKNAME, U.USER_PIC USER_PIC, T.TWEET_DATE TWEET_DATE, T.TEXT TEXT" + 
                                                            " FROM (select id from `USER` where nickname = ?) my_user " + 
                                                                " INNER JOIN `FOLLOWING_RELATION` FR ON my_user.id = FR.follower_id " + 
                                                                " INNER JOIN `TWEET` T ON FR.followed_id = T.user_id " + 
                                                                " INNER JOIN `USER` U ON FR.followed_id = U.id " + 
                                                             " ORDER BY TWEET_DATE DESC ";
    
    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                                        .withTableName("TWEET")
                                        .usingGeneratedKeyColumns("id");
    }
    
    public List<TweetInfo> fetchRecentTweets(int limit) {
        try {
            return jdbcTemplate.query(SQL_FETCH_RECENT_TWEETS, tweetInfoMapper, limit);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return Collections.emptyList();
        }
    }
    
    public List<TweetInfo> fetchUserTweets(Long userId) {
        try {
            return jdbcTemplate.query(SQL_FETCH_USER_TWEETS, tweetInfoMapper, userId);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return Collections.emptyList();
        }
    }
    
    public List<TweetInfo> fetchUserTimeLineTweets(String username) {
        try {
            return jdbcTemplate.query(SQL_FETCH_TIMELINE_TWEETS, tweetInfoMapper, username);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return Collections.emptyList();
        }
    }
    
    public void saveUserTweet(final Long userId, final String text, final DateTime date) {
        jdbcInsert.execute(new HashMap<String, Object>() {{
            put("USER_ID", userId);
            put("TWEET_DATE", date.toDate());
            put("TEXT", text);
        }});
    }
    
}

class TweetInfoMapper implements RowMapper<TweetInfo> {

    public TweetInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
        TweetInfo result = new TweetInfo();
        
        result.setId(rs.getLong("ID"));
        result.setUserId(rs.getLong("USER_ID"));
        result.setNickname(rs.getString("NICKNAME"));
        result.setTweetDate(new DateTime(rs.getDate("TWEET_DATE")));
        result.setText(rs.getString("TEXT"));
        result.setUserPic(rs.getString("USER_PIC"));
        
        return result;
    }
    
}