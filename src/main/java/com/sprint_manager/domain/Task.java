package com.sprint_manager.domain;

import java.util.Date;

public class Task {

    private Integer id;
    private String number;
    private String title;

    private Integer authorId;
    private Integer sprintId;
    private String authorName;

    private Date startDate;
    private Date endDate;

    private TaskState taskState;
    private Integer estimate;

    public String getTaskPriority() {
        return taskPriority.name();
    }

    public void setTaskPriority(TaskPriority taskPriority) {
        this.taskPriority = taskPriority;
    }

    private TaskPriority taskPriority;

    public Task() {

    }


    public Task(String title, String number, Date start, Date end) {
        this.number = number;
        this.startDate = start;
        this.endDate = end;
    }

    public Integer getSprintId() {
        return sprintId;
    }

    public void setSprintId(Integer sprintId) {
        this.sprintId = sprintId;
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

    public String getTaskState() {
        return taskState.name();
    }

    public void setTaskState(TaskState taskState) {
        this.taskState = taskState;
    }

    public Integer getEstimate() {
        return estimate;
    }

    public void setEstimate(Integer rate) {
        this.estimate = rate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
//        return endDate==null?null:endDate.toString();
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }


}
