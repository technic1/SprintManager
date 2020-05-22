package com.example.SprintManager.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Task {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    private String number;

    private String startDate;

    private String endDate;

    public Task() {

    }

    public Task(String number, String start, String end) {
        this.number = number;
        this.startDate = start;
        this.endDate = end;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getStart() {
        return startDate;
    }

    public void setStart(String start) {
        this.startDate = start;
    }

    public String getEnd() {
        return endDate;
    }

    public void setEnd(String end) {
        this.endDate = end;
    }
}
