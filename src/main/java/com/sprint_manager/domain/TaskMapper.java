package com.sprint_manager.domain;


import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TaskMapper implements RowMapper<Task> {

    @Override
    public Task mapRow(ResultSet rs, int rowNum) throws SQLException {

        Task task = new Task();
        task.setId(rs.getInt("id"));
        task.setTitle(rs.getString("title"));
        task.setNumber(rs.getString("number"));
        task.setStartDate(rs.getDate("date_start").toString());
        try {
            task.setEndDate(rs.getDate("date_end").toString());
        } catch (NullPointerException e) {
            task.setEndDate(null);
        }
        task.setTaskState(TaskState.valueOf(rs.getString("state")));
        task.setTaskPriority(TaskPriority.valueOf(rs.getString("priority")));
        task.setRate(rs.getInt("rate"));
        task.setAuthorName(rs.getString("full_name"));

        return task;
    }
}