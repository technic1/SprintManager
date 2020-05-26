package com.sprint_manager.service;

import com.sprint_manager.domain.Task;
import com.sprint_manager.domain.TaskPriority;
import com.sprint_manager.domain.TaskState;
import com.sprint_manager.repos.TaskRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;
import java.util.OptionalInt;
import java.util.stream.Stream;

@Service
public class TaskService {
    @Autowired
    private TaskRepo taskRepo;

    public void addTask (String title, String start, String priority, String rate) throws ParseException {
        Task task = new Task();

        task.setTitle(title);
        task.setStartDate(start);
        task.setTaskPriority(TaskPriority.valueOf(priority));
        task.setRate(Integer.valueOf(rate));

        task.setAuthorId(1);
        task.setTaskState(TaskState.DRAFT);

        String number = "PRJ-";
        List<Task> tasks = taskRepo.findAll();
        OptionalInt max = tasks.stream()
                .map(t -> t.getNumber())
                .map(s -> s.substring(s.length() - 3))
                .mapToInt(Integer::valueOf)
                .max();

        //adding 1 to max int
        String max_int = Integer.valueOf(max.getAsInt() + 1).toString();
        while (max_int.length() < 3) max_int = "0" + max_int;

        number += max_int;
        task.setNumber(number);

        taskRepo.save(task);
    }
}
