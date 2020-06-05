package com.sprint_manager.repos;

import com.sprint_manager.domain.Sprint;
import com.sprint_manager.domain.SprintMapper;
import com.sprint_manager.domain.SprintState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;

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

    public Integer insertNewTask(Sprint sprint) {
        return jdbcTemplate.queryForObject(
                "insert into sprints (title, author_id, state, date_start, date_end_expect) " +
                        "values(?, ?, ?, ?, ?) returning id",
                new Object[] {
                        sprint.getTitle(),
                        sprint.getAuthorId(),
                        sprint.getSprintState(),
                        sprint.getStartDate(),
                        sprint.getEndDateExpect()
                },
                Integer.class
        );
    }

    public Sprint getSprintById(Integer id) {
        return jdbcTemplate.queryForObject(
                "select s.id, u.full_name, s.title, s.state, " +
                        "s.date_start, s.date_end_expect, s.date_end_fact " +
                        "from sprints as s inner join users as u on s.author_id = u.id " +
                        "where s.id = ?",
                new Object[] {id},
                new SprintMapper()
        );
    }

    public boolean updateSprintStateAndActEndDate(Integer id, String state, Date dateEndFact) {
        return jdbcTemplate.update(
                "update sprints set state = ?, date_end_fact = ? where id = ?",
                state,
                dateEndFact,
                id
        ) == 1;
    }

    public Sprint getActiveSprint() {
        try {
            return jdbcTemplate.queryForObject(
                    "select s.id, u.full_name, s.title, s.state, " +
                            "s.date_start, s.date_end_expect, s.date_end_fact " +
                            "from sprints as s inner join users as u on s.author_id = u.id " +
                            "where s.state = ?",
                    new Object[] { SprintState.STARTED.toString() },
                    new SprintMapper()
            );
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }

    public boolean updateSprint(Sprint sprint) {
        return jdbcTemplate.update(
                "update sprints set title = ?, date_start = ?, date_end_expect = ? where sprint.id = ?",
                sprint.getTitle(),
                sprint.getStartDate(),
                sprint.getEndDateExpect(),
                sprint.getId()
        ) == 1;
    }

    public Map<String, Integer> getCountTasksAndSumEstimate(Integer id) {

        Map<String, Object> stringObjectMap = jdbcTemplate.queryForMap(
                "select sum(estimate), count(estimate) from tasks where sprint_id = ?",
                new Object[] { id }
        );
        Map<String, Integer> map = new HashMap<>();
        map.put("sum", ((Long) stringObjectMap.get("sum")).intValue());
        map.put("count", ((Long) stringObjectMap.get("count")).intValue());
        return map;
    }
}
