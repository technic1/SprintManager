package com.sprint_manager.repos;

import com.sprint_manager.domain.Sprint;
import com.sprint_manager.domain.SprintMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SprintRepo {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Sprint> getAllSprints() {
        return jdbcTemplate.query(
                "select sprints.id, users.full_name, sprints.title, sprints.state, " +
                        "sprints.date_start, sprints.date_end_expect, sprints.date_end_fact" +
                        "  from sprints inner join users on sprints.author_id = users.id",
                new SprintMapper()
        );
    }

    public Integer getCountOfTasks(Integer id) {
        return jdbcTemplate.queryForObject(
                "select count(*) from sprint_backlog where sprint_id = ?",
                new Object[] { id },
                Integer.class
        );
    }
}
