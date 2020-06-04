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

    public Sprint insertNewSprint(User user, String title, String start, String end) throws ParseException {
        Sprint sprint = new Sprint();

        sprint.setTitle(title);
        sprint.setAuthorId(user.getId());
        Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse(start);
        Date endDateExpect = new SimpleDateFormat("yyyy-MM-dd").parse(end);

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

    //fix it with one sql request?
    public Sprint getSprintById(Integer id) {
        Sprint sprint = sprintRepo.getSprintById(id);
        Map<String, Integer> sumEstimateAndCount = sprintRepo.getCountTasksAndSumEstimate(id);

        sprint.setEstimate(sumEstimateAndCount.get("sum"));
        sprint.setCountTasks(sumEstimateAndCount.get("count"));

        return sprint;
    }

    public void startSprint(Integer id) {
        sprintRepo.updateSprintStateAndActEndDate(id, SprintState.STARTED.name(), null);
    }

    public void finishSprint(Integer id) {
        Date dateEndFact = new Date();
        sprintRepo.updateSprintStateAndActEndDate(id, SprintState.FINISHED.name(), dateEndFact);
    }

    public boolean haveActiveSprint() {
        Sprint activeSprint = sprintRepo.getActiveSprint();
        if (activeSprint != null) {
            return true;
        } else {
            return false;
        }
    }

    public boolean haveOpenTasksBySprintId(Integer id) {
        List<Task> allOpenTasksById = taskRepo.getAllOpenTasksById(id);
        if (allOpenTasksById.size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public Sprint getActiveSprint() {
        return sprintRepo.getActiveSprint();
    }

    public boolean haveTasks(Integer id) {
        List<Task> allTasksBySprintId = taskRepo.getAllTasksBySprintId(id);

        if (allTasksBySprintId.size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean editSprint(String sprintId, String title, String startDate, String endDateExpect) throws ParseException {

        Sprint sprint = new Sprint();
        sprint.setId(Integer.valueOf(sprintId));
        sprint.setTitle(title);
        Date start = new SimpleDateFormat("yyyy-MM-dd").parse(startDate);
        Date end = new SimpleDateFormat("yyyy-MM-dd").parse(endDateExpect);

        sprint.setStartDate(start);
        sprint.setEndDateExpect(end);

        return sprintRepo.updateSprint(sprint);
    }
}
