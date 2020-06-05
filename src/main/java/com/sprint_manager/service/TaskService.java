package com.sprint_manager.service;

import com.sprint_manager.domain.Task;
import com.sprint_manager.domain.TaskPriority;
import com.sprint_manager.domain.TaskState;
import com.sprint_manager.domain.User;
import com.sprint_manager.repos.TaskRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TaskService {
    @Autowired
    private TaskRepo taskRepo;

    public boolean addTask (User user, Task task) {
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

    public boolean setTaskOpen(String taskId) {
        Task task = new Task();
        task.setId(Integer.valueOf(taskId));
        task.setEndDate(null);

        return taskRepo.updateTaskStateAndEndDate(TaskState.OPEN.toString(), task);
    }
}
