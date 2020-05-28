package com.sprint_manager.domain;

import java.util.Date;

public class Sprint {

    private Integer id;
    private String title;
    private Integer authorId;
    private String authorName;
    private SprintState sprintState;
    private Date startDate;
    private Date endDateExpect;
    private Date endDateFact;
    private Integer countTasks;

    public Sprint() {

    }

    public Sprint(Integer id, Integer authorId, String authorName, SprintState sprintState, Date startDate, Date endDateExpect, Date endDateFact) {
        this.id = id;
        this.authorId = authorId;
        this.authorName = authorName;
        this.sprintState = sprintState;
        this.startDate = startDate;
        this.endDateExpect = endDateExpect;
        this.endDateFact = endDateFact;
    }

    public Integer getCountTasks() {
        return countTasks;
    }

    public void setCountTasks(Integer countTasks) {
        this.countTasks = countTasks;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public SprintState getSprintState() {
        return sprintState;
    }

    public void setSprintState(SprintState sprintState) {
        this.sprintState = sprintState;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDateExpect() {
        return endDateExpect;
    }

    public void setEndDateExpect(Date endDateExpect) {
        this.endDateExpect = endDateExpect;
    }

    public Date getEndDateFact() {
        return endDateFact;
    }

    public void setEndDateFact(Date endDateFact) {
        this.endDateFact = endDateFact;
    }
}
