package com.sprint_manager.domain;

public class Task {

    private Integer id;
    private String number;
    private String title;

    private User author;

    private String startDate;
    private String endDate;

    private TaskState taskState;
    private Integer rate;

    public Task() {

    }


    public Task(String title, String number, String start, String end) {
        this.number = number;
        this.startDate = start;
        this.endDate = end;
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
