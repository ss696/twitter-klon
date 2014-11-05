package org.oi.twitter.dao;

import java.util.HashMap;
import java.util.List;

import org.oi.twitter.domain.User;
import org.oi.twitter.domain.ui.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.oi.twitter.crypt.BCryptutil;

@Repository
public class UserDao {
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert jdbcInsert;
    private static final String SQL_FETCH_USER_BY_ID = "SELECT * FROM `USER` WHERE ID = ?";
    private static final String SQL_FETCH_USER_BY_NAME = "SELECT * FROM `USER` WHERE NICKNAME = ?";
    private static final String SQL_FETCH_USERS_COUNT_BY_NAME = "SELECT COUNT(1) USERCOUNT FROM `USER` WHERE NICKNAME = ?";
    private static final String SQL_FETCH_USERS = "SELECT * FROM `USER`";
    private static final String SQL_DELETE_USER = "DELETE FROM USER WHERE ID = ?";
    private static final String SQL_UPDATE_USER = "UPDATE USER SET FIRSTNAME=?, LASTNAME=?, PASSWORD=?, EMAIL=?, UPDATE_BY_EMAIL=?, USER_PIC=?  WHERE ID = ?";
    private static final String SQL_FETCH_USERINFO_LIST = "select u.id id, u.nickname nickname, u.firstname firstname, u.lastname lastname, " + 
                                                           " u.password password, u.email email, u.update_by_email update_by_email, " +
                                                           " u.user_pic user_pic, case when fr.follower_id is null then 0 else 1 end is_following " +
                                                                " from `USER` u join (select id from `USER` where nickname = ?) my_usr " +
                                                                                    " ON u.id <> my_usr.id " +
                                                                                " left join FOLLOWING_RELATION fr " +
                                                                                    " ON fr.follower_id = my_usr.id and fr.followed_id = u.id";
    private static final String SQL_FETCH_USERINFO = "select u.id id, u.nickname nickname, u.firstname firstname, u.lastname lastname, " + 
                                                        " u.password password, u.email email, u.update_by_email update_by_email, " +
                                                        " u.user_pic user_pic, case when fr.follower_id is null then 0 else 1 end is_following " +
                                                            " from `USER` u left join FOLLOWING_RELATION fr " +
                                                                        " on u.id = fr.followed_id AND (select id from `USER` where nickname = ?) " +
                                                     " where u.nickname = ?"; 
            
    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                                        .withTableName("USER")
                                        .usingGeneratedKeyColumns("id");
    }
    
    public List<User> listUsers() {
        return jdbcTemplate.query(SQL_FETCH_USERS, new BeanPropertyRowMapper<User>(User.class));
    }
    
    public List<UserInfo> fetchUserInfoListForUser(String username) {//fetch all usres except user
        return jdbcTemplate.query(SQL_FETCH_USERINFO_LIST, new BeanPropertyRowMapper<UserInfo>(UserInfo.class), username);
    }
    
    public User fetchUserById(Long id) {
        return jdbcTemplate.queryForObject(SQL_FETCH_USER_BY_ID, new BeanPropertyRowMapper<User>(User.class), id); 
    }
    
    public User fetchUserByName(String name) {
        return jdbcTemplate.queryForObject(SQL_FETCH_USER_BY_NAME, new BeanPropertyRowMapper<User>(User.class), name);
    }
    
    public boolean checkIfUsernameIsAvailable(String nickname) {
        return jdbcTemplate.queryForInt(SQL_FETCH_USERS_COUNT_BY_NAME, nickname) > 0;
    }
    
    public void saveNewUser(final User user) {
        String pwd = user.getPassword();
        final String computed_hash = BCryptutil.hashPassword(pwd);
        Number newId = jdbcInsert.executeAndReturnKey(new HashMap<String, Object>() {{
                                                            put("nickname", user.getNickname());
                                                            put("firstname", user.getFirstname());
                                                            put("lastname", user.getLastname());
                                                            put("password", computed_hash);
                                                            put("email", user.getEmail());
                                                            put("update_by_email", user.getUpdateByEmail());
                                                            put("user_pic", user.getUserPic());
                                                       }});
        user.setId(newId.longValue());
    }

    public void deleteUser(User user) {
        jdbcTemplate.update(SQL_DELETE_USER, user.getId());
    }

    public void updateUser(User user) {
        jdbcTemplate.update(SQL_UPDATE_USER, user.getFirstname(), user.getLastname(), user.getPassword(), user.getEmail(), user.getUpdateByEmail(), user.getUserPic(), user.getId());
    }

    public UserInfo fetchUserInfo(String username, String authenticatedUserName) {
        return jdbcTemplate.queryForObject(SQL_FETCH_USERINFO, new BeanPropertyRowMapper<UserInfo>(UserInfo.class), authenticatedUserName, username);
    }
}
