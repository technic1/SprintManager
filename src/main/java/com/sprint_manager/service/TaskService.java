package com.sprint_manager.service;

import com.sprint_manager.domain.Task;
import com.sprint_manager.domain.TaskPriority;
import com.sprint_manager.domain.TaskState;
import com.sprint_manager.domain.User;
import com.sprint_manager.repos.TaskRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.OptionalInt;

@Service
public class TaskService {
    @Autowired
    private TaskRepo taskRepo;

    public void addTask (User user, String title, String priority, String rate) throws ParseException {
        Task task = new Task();

        task.setTitle(title);

        task.setStartDate(new Date());
        task.setTaskPriority(TaskPriority.valueOf(priority));
        task.setEstimate(Integer.valueOf(rate));

        //3 for admin
        task.setAuthorId(user.getId());
        task.setTaskState(TaskState.OPEN);

        String number = "PRJ-";
        List<Task> tasks = taskRepo.getAllTasks();
        OptionalInt max = tasks.stream()
                .map(t -> t.getNumber())
                .map(s -> s.substring(s.length() - 3))
                .mapToInt(Integer::valueOf)
                .max();

        //adding 1 to max number
        String maxInt = Integer.valueOf(max.getAsInt() + 1).toString();
        while (maxInt.length() < 3) maxInt = "0" + maxInt;

        number += maxInt;
        task.setNumber(number);

        taskRepo.createTask(task);
    }

    public void deleteTask(String task_id) {
        taskRepo.deleteTask(Integer.valueOf(task_id));
    }

    public void editTask(String id, String title, String priority, String state, String estimate) throws ParseException {
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
