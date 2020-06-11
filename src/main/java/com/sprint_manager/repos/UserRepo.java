package com.sprint_manager.repos;

import com.sprint_manager.domain.User;
import com.sprint_manager.domain.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class UserRepo {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public User findByUserName(String username) {
        return DataAccessUtils.singleResult(
                jdbcTemplate.query(
                        "select * from users where full_name = ?",
                        new Object[]{ username },
                        new UserMapper()
                ));
    }

    public boolean saveUser(User user) {
        return jdbcTemplate.update(
                "insert into users (full_name, password, role) " +
                        "values(?, ?, ?)",
                user.getUsername(),
                user.getPassword(),
                user.getRole().name()
        ) == 1;
    }
}
