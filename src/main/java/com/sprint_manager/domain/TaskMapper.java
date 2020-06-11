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

        String number = rs.getString("number");
        String numberCode = Integer.valueOf(rs.getInt("number_int")).toString();
        while (numberCode.length() < 3) numberCode = "0" + numberCode;

        task.setNumber(number + numberCode);
        task.setStartDate(rs.getDate("date_start"));
        task.setEndDate(rs.getDate("date_end"));
        task.setTaskState(TaskState.valueOf(rs.getString("state")));
        task.setTaskPriority(TaskPriority.valueOf(rs.getString("priority")));
        task.setEstimate(rs.getInt("estimate"));
        task.setAuthorName(rs.getString("full_name"));

        return task;
    }
}