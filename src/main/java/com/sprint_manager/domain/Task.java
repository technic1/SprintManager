package com.sprint_manager.domain;

public class Task {

    private Integer id;
    private String number;
    private String title;

    private Integer authorId;
    private String authorName;

    private String startDate;
    private String endDate;

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


    public Task(String title, String number, String start, String end) {
        this.number = number;
        this.startDate = start;
        this.endDate = end;
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

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }


}
