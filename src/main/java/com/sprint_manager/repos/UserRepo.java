package com.sprint_manager.repos;

import com.sprint_manager.domain.User;
import com.sprint_manager.domain.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepo {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<User> findByUserName(String username) {
        return jdbcTemplate.query(
                "select * from users where full_name = ?",
                new Object[] { username },
                new UserMapper()
        );

    }

    public void saveUser(User user) {
        jdbcTemplate.update(
                "insert into users (full_name, password, role) " +
                        "values(?, ?, ?)",
                user.getUsername(),
                user.getPassword(),
                user.getRole().name()
        );
    }
}
