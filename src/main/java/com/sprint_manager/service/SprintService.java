package com.sprint_manager.service;

import com.sprint_manager.domain.Task;
import com.sprint_manager.repos.SprintRepo;
import com.sprint_manager.repos.TaskRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
