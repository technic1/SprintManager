package com.sprint_manager.service;

import com.sprint_manager.domain.Task;
import com.sprint_manager.domain.TaskPriority;
import com.sprint_manager.domain.TaskState;
import com.sprint_manager.repos.TaskRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.OptionalInt;

@Service
public class TaskService {
    @Autowired
    private TaskRepo taskRepo;

    public void addTask (String title, String priority, String rate) throws ParseException {
        Task task = new Task();

        task.setTitle(title);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String startDate = formatter.format(new Date());
        task.setStartDate(startDate);
        task.setTaskPriority(TaskPriority.valueOf(priority));
        task.setEstimate(Integer.valueOf(rate));

        //1 for admin
        task.setAuthorId(1);
        task.setTaskState(TaskState.DRAFT);

        String number = "PRJ-";
        List<Task> tasks = taskRepo.findAll();
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

        taskRepo.save(task);
    }

    public void deleteTask(String task_id) {
        taskRepo.delete(Integer.valueOf(task_id));
    }
}
