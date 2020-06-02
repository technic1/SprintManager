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

//        for (Sprint sprint : sprints) {
//            List<Task> tasks = taskRepo.getAllTasksBySprintId(sprint.getId());
//            sprint.setCountTasks(tasks.size());
//
//            int estimate = tasks.stream()
//                    .map(t -> t.getEstimate())
//                    .mapToInt(Integer::intValue)
//                    .sum();
//
//            sprint.setEstimate(estimate);
//        }
        return sprints;
    }

    public Sprint getSprintById(Integer id) {
        Sprint sprint = sprintRepo.getSprintById(id);

        List<Task> tasks = taskRepo.getAllTasksBySprintId(sprint.getId());
        sprint.setCountTasks(tasks.size());

        int estimate = tasks.stream()
                .map(t -> t.getEstimate())
                .mapToInt(Integer::intValue)
                .sum();

        sprint.setEstimate(estimate);

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
        List<Sprint> activeSprints = sprintRepo.getActiveSprints();
        if (activeSprints.size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean haveOpenTasksBySprintId(Integer id) {

        return taskRepo.getAllTasksBySprintId(id).stream()
                .map(t -> t.getTaskState())
                .anyMatch(state -> state.equals(TaskState.OPEN.name()));
    }


    public Sprint getActiveSprint() {
        List<Sprint> activeSprints = sprintRepo.getActiveSprints();
        if (activeSprints.size() > 0) {
            return activeSprints.get(0);
        } else {
            return null;
        }
    }

    public boolean haveTasks(Integer id) {
        List<Task> allTasksBySprintId = taskRepo.getAllTasksBySprintId(id);

        if (allTasksBySprintId.size() > 0) {
            return true;
        } else {
            return false;
        }

    }
}
