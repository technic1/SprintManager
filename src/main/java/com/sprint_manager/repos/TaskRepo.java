package com.sprint_manager.repos;

import com.sprint_manager.domain.Task;
import com.sprint_manager.domain.TaskMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TaskRepo {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int save(Task test) {
        return jdbcTemplate.update(
                "insert into tasks () values"
        );
    }

    public List<Task> findAll() {
        return jdbcTemplate.query(
                "select * from tasks", new TaskMapper()
        );
    }

}
