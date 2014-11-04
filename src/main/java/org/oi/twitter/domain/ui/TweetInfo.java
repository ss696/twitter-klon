package org.oi.twitter.domain.ui;

import org.joda.time.DateTime;

public class TweetInfo {
    private Long id;
    private Long userId;
    private DateTime tweetDate;
    private String text;
    private String nickname;
    private String userPic;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public DateTime getTweetDate() {
        return tweetDate;
    }

    public void setTweetDate(DateTime tweetDate) {
        this.tweetDate = tweetDate;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getUserPic() {
        return userPic;
    }

    public void setUserPic(String userPic) {
        this.userPic = userPic;
    }
}
