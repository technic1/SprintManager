package com.sprint_manager.domain;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;

public class UserMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();

        user.setId(rs.getInt("id"));
        user.setUsername(rs.getString("full_name"));
        user.setPassword(rs.getString("password"));

        HashSet<UserRole> roles = new HashSet<>();
        roles.add(UserRole.valueOf(rs.getString("role")));

        user.setRoles(roles);

        return user;
    }
}
