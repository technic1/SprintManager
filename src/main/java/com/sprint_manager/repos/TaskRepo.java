package com.sprint_manager.repos;

import com.sprint_manager.domain.Task;
import com.sprint_manager.domain.TaskMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@Repository
public class TaskRepo {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int save(Task task) throws ParseException {
        return jdbcTemplate.update(
                "insert into tasks " +
                        "(author_id, date_start, state, title, number, estimate, priority) " +
                        "values(?,?,?,?,?,?,?)",
                task.getAuthorId(),
                new SimpleDateFormat("yyyy-MM-dd").parse(task.getStartDate()),
                task.getTaskState(),
                task.getTitle(),
                task.getNumber(),
                task.getEstimate(),
                task.getTaskPriority()
        );
    }

    public List<Task> findAll() {
        return jdbcTemplate.query(
                "select tasks.id, users.full_name, tasks.date_start, " +
                        "tasks.date_end, tasks.state, tasks.title, tasks.number, " +
                        "tasks.estimate, tasks.priority  " +
                        "from tasks inner join users on tasks.author_id = users.id",
                new TaskMapper()
        );
    }

    public boolean delete(Integer id) {
        return jdbcTemplate.update(
                "delete from tasks where id = ?",
                id
        ) > 0;
    }
}
