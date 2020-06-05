package com.sprint_manager.service;

import com.sprint_manager.domain.*;
import com.sprint_manager.repos.TaskRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.Date;

@Service
public class TaskService {
    @Autowired
    private TaskRepo taskRepo;

    public boolean addTask (User user, String title, String priority, String estimate) {
        Task task = new Task();

        task.setTitle(title);
        task.setTaskPriority(TaskPriority.valueOf(priority));
        task.setEstimate(Integer.valueOf(estimate));
        task.setStartDate(new Date());
        task.setAuthorId(user.getId());
        task.setTaskState(TaskState.OPEN);

        String number = "PRJ-";
        task.setNumber(number);

        return taskRepo.createTask(task);
    }

    public void deleteTask(String task_id) {
        taskRepo.deleteTask(Integer.valueOf(task_id));
    }

    public void editTask(String id, String title, String priority, String state, String estimate) {
        Task task = new Task();

        task.setId(Integer.valueOf(id));
        task.setTitle(title);
        task.setTaskPriority(TaskPriority.valueOf(priority));

        TaskState taskState = TaskState.valueOf(state);
        task.setTaskState(taskState);

        if (taskState == TaskState.CLOSED) {
            task.setEndDate(new Date());
        } else {
            task.setEndDate(null);
        }
        task.setEstimate(Integer.valueOf(estimate));

        taskRepo.updateTask(task);

    }

    public boolean setTaskClosed(String taskId) {
        Task task = new Task();
        task.setId(Integer.valueOf(taskId));
        task.setEndDate(new Date());

        return taskRepo.updateTaskStateAndEndDate(TaskState.CLOSED.toString(), task);
    }

    public void editTaskIfDeveloperHaveAccess(Model model, User user, String authorName, String id, String title,
                                              String priority, String state,String estimate) {
        if (user.getRole() == UserRole.DEVELOPER) {
            if (user.getUsername().equals(authorName)) {
                editTask(id, title, priority, state, estimate);
            } else {
                model.addAttribute("errorAccessDenied", "Access denied!");
            }
        }
    }
}
