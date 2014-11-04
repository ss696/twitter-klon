package org.oi.twitter.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserRoleDao {
    private @Autowired JdbcTemplate jdbcTemplate;
    private static final String SQL_ADD_USER_ROLE = "INSERT INTO USER_ROLES(USER_ID, AUTHORITY) VALUES(?, ?)";
    
    public void addUserRole(Long userId, String roleName) {
        jdbcTemplate.update(SQL_ADD_USER_ROLE, userId, roleName);
    }
    
}
