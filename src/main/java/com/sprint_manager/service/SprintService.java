package com.sprint_manager.service;

import com.sprint_manager.domain.Sprint;
import com.sprint_manager.domain.SprintState;
import com.sprint_manager.domain.Task;
import com.sprint_manager.repos.SprintRepo;
import com.sprint_manager.repos.TaskRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.DateFormatter;
import java.text.DateFormat;
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

    public Sprint insertNewTask(String title, String start, String end) throws ParseException {
        Sprint sprint = new Sprint();

        sprint.setTitle(title);
        sprint.setAuthorId(1);
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

        for (Sprint sprint : sprints) {
            List<Task> tasks = taskRepo.getAllTasksBySprintId(sprint.getId());
            sprint.setCountTasks(tasks.size());

            int estimate = tasks.stream()
                    .map(t -> t.getEstimate())
                    .mapToInt(Integer::intValue)
                    .sum();

            sprint.setEstimate(estimate);
        }
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
}
