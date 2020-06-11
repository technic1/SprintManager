package com.sprint_manager.domain;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SprintMapper implements RowMapper<Sprint> {

    @Override
    public Sprint mapRow(ResultSet rs, int rowNum) throws SQLException {
        Sprint sprint = new Sprint();

        sprint.setId(rs.getInt("id"));
        sprint.setTitle(rs.getString("title"));
        sprint.setAuthorName(rs.getString("full_name"));
        sprint.setSprintState(SprintState.valueOf(rs.getString("state")));
        sprint.setStartDate(rs.getDate("date_start"));
        sprint.setEndDateExpect(rs.getDate("date_end_expect"));
        sprint.setEndDateFact(rs.getDate("date_end_fact"));

        return sprint;
    }
}
