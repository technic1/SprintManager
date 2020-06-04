package com.sprint_manager.repos;

import com.sprint_manager.domain.Task;
import com.sprint_manager.domain.TaskMapper;
import com.sprint_manager.domain.TaskState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.text.ParseException;
import java.util.List;

@Repository
public class TaskRepo {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int createTask(Task task) throws ParseException {
        return jdbcTemplate.update(
                "insert into tasks " +
                        "(author_id, date_start, state, title, number, estimate, priority) " +
                        "values(?,?,?,?,?,?,?)",
                task.getAuthorId(),
                task.getStartDate(),
                task.getTaskState(),
                task.getTitle(),
                task.getNumber(),
                task.getEstimate(),
                task.getTaskPriority()
        );
    }

    public List<Task> getAllTasks() {
        return jdbcTemplate.query(
                "select tasks.id, users.full_name, tasks.date_start, " +
                        "tasks.date_end, tasks.state, tasks.title, tasks.number, " +
                        "tasks.estimate, tasks.priority  " +
                        "from tasks inner join users on tasks.author_id = users.id order by tasks.number",
                new TaskMapper()
        );
    }

    public boolean deleteTask(Integer id) {
        return jdbcTemplate.update(
                "delete from tasks where id = ?",
                id
        ) > 0;
    }

    public boolean updateTask(Task task) throws ParseException {
        return jdbcTemplate.update(
                "update tasks set title = ?, date_end = ?, priority = ?, state = ?, estimate = ? where id = ?",
                task.getTitle(),
                task.getEndDate(),
                task.getTaskPriority(),
                task.getTaskState(),
                task.getEstimate(),
                task.getId()
        ) > 0;
    }

    public List<Task> getAllTasksBySprintId(Integer id) {
        return jdbcTemplate.query(
                "select t.id, u.full_name, t.date_start, " +
                        "t.date_end, t.state, t.title, t.number, " +
                        "t.estimate, t.priority  " +
                        "from tasks as t inner join users as u on t.author_id = u.id " +
                        " where t.sprint_id = ? order by t.priority",
                new Object[] { id },
                new TaskMapper()
        );
    }

    public List<Task> getAllTasksFromBacklog() {
        return jdbcTemplate.query(
                "select t.id, users.full_name, t.date_start, " +
                        "t.date_end, t.state, t.title, t.number, " +
                        "t.estimate, t.priority  " +
                        "from tasks as t inner join users on t.author_id = users.id " +
                        "where t.sprint_id is null order by t.number",
                new TaskMapper()
        );
    }

    //adding sprint_id to task
    public boolean updateTaskSprintId(Task task) {
        return jdbcTemplate.update(
                "update tasks set sprint_id = ? where id = ?",
                task.getSprintId(),
                task.getId()
        ) > 0;
    }

    public boolean deleteTaskFromSprint(Integer id) {
        return jdbcTemplate.update(
                "update tasks set sprint_id = null where id = ?",
                id
        ) > 0;
    }

    public boolean updateTaskStateAndEndDate(String state, Task task) {
        return jdbcTemplate.update(
                "update tasks set state = ?, date_end = ? where id = ?",
                state,
                task.getEndDate(),
                task.getId()
        ) > 0;
    }

    public List<Task> getAllOpenTasksById(Integer id) {
        return jdbcTemplate.query(
                "select t.id, u.full_name, t.date_start, " +
                        "t.date_end, t.state, t.title, t.number, " +
                        "t.estimate, t.priority  " +
                        "from tasks as t inner join users as u on t.author_id = u.id " +
                        " where t.sprint_id = ? and t.state = ?",
                new Object[] { id, TaskState.OPEN.toString()},
                new TaskMapper()
        );
    }
}
