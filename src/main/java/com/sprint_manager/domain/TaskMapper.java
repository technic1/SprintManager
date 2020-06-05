package com.sprint_manager.domain;


import org.springframework.jdbc.core.RowMapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class TaskMapper implements RowMapper<Task> {

    @Override
    public Task mapRow(ResultSet rs, int rowNum) throws SQLException {
        Task task = new Task();

        task.setId(rs.getInt("id"));
        task.setTitle(rs.getString("title"));

        String number = rs.getString("number");
        String number_code = Integer.valueOf(rs.getInt("number_int")).toString();
        while (number_code.length() < 3) number_code = "0" + number_code;

        task.setNumber(number + number_code);
        task.setStartDate(rs.getDate("date_start"));
        task.setEndDate(rs.getDate("date_end"));
        task.setTaskState(TaskState.valueOf(rs.getString("state")));
        task.setTaskPriority(TaskPriority.valueOf(rs.getString("priority")));
        task.setEstimate(rs.getInt("estimate"));
        task.setAuthorName(rs.getString("full_name"));

        return task;
    }
}