package com.sprint_manager.service;

import com.sprint_manager.domain.*;
import com.sprint_manager.repos.SprintRepo;
import com.sprint_manager.repos.TaskRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class SprintService {
    @Autowired
    private SprintRepo sprintRepo;

    @Autowired
    private TaskRepo taskRepo;

    public void addTaskToSprint(String sprintId, String taskId) {
        Task task = new Task();
        task.setId(Integer.valueOf(taskId));
        task.setSprintId(Integer.valueOf(sprintId));

        taskRepo.updateTaskSprintId(task);
    }

    public Sprint insertNewSprint(User user, String title, String start, String end) throws RuntimeException {
        Sprint sprint = new Sprint();

        sprint.setTitle(title);
        sprint.setAuthorId(user.getId());

        Date startDate = null;
        try {
            startDate = new SimpleDateFormat("yyyy-MM-dd").parse(start);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        Date endDateExpect = null;
        try {
            endDateExpect = new SimpleDateFormat("yyyy-MM-dd").parse(end);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        sprint.setStartDate(startDate);
        sprint.setEndDateExpect(endDateExpect);
        sprint.setSprintState(SprintState.DRAFT);

        Integer id = sprintRepo.insertNewTask(sprint);
        sprint.setId(id);

        return sprint;
    }

    public List<Sprint> getAllSprints() {
        List<Sprint> sprints = sprintRepo.getAllSprints();

        return sprints;
    }

    public Sprint getSprintById(String sprintId) {
        Integer id = Integer.valueOf(sprintId);

        Sprint sprint = sprintRepo.getSprintById(id);
        Map<String, Integer> sumEstimateAndCount = sprintRepo.getCountTasksAndSumEstimate(id);

        sprint.setEstimate(sumEstimateAndCount.get("sum"));
        sprint.setCountTasks(sumEstimateAndCount.get("count"));

        return sprint;
    }

    public void startSprint(String sprintId) {
        Integer id = Integer.valueOf(sprintId);

        sprintRepo.updateSprintStateAndActEndDate(id, SprintState.STARTED.name(), null);
    }

    public void finishSprint(String sprintId) {
        Integer id = Integer.valueOf(sprintId);

        Date dateEndFact = new Date();
        sprintRepo.updateSprintStateAndActEndDate(id, SprintState.FINISHED.name(), dateEndFact);
    }

    public boolean haveActiveSprint() {
        Sprint activeSprint = sprintRepo.getActiveSprint();

        return activeSprint != null;
    }

    public boolean haveOpenTasksBySprintId(String sprintId) {
        Integer id = Integer.valueOf(sprintId);

        List<Task> allOpenTasksById = taskRepo.getAllOpenTasksById(id);

        return allOpenTasksById.size() > 0;
    }

    public Sprint getActiveSprint() {
        return sprintRepo.getActiveSprint();
    }

    public boolean haveTasks(String sprintId) {

        List<Task> allTasksBySprintId = taskRepo.getAllTasksBySprintId(sprintId);

        return  allTasksBySprintId.size() > 0;
    }

    public boolean editSprint(String sprintId, String title, String startDate, String endDateExpect) throws RuntimeException {
        Sprint sprint = new Sprint();
        sprint.setId(Integer.valueOf(sprintId));
        sprint.setTitle(title);

        Date start = null;
        try {
            start = new SimpleDateFormat("yyyy-MM-dd").parse(startDate);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        Date end = null;
        try {
            end = new SimpleDateFormat("yyyy-MM-dd").parse(endDateExpect);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        sprint.setStartDate(start);
        sprint.setEndDateExpect(end);

        return sprintRepo.updateSprint(sprint);
    }
}
