package com.sprint_manager.controller;

import com.sprint_manager.domain.Task;
import com.sprint_manager.repos.TaskRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class GreetingController {
    @Autowired
    private TaskRepo taskRepo;

    @GetMapping("/greeting")
    public String greeting(
            @RequestParam(name="name", required=false, defaultValue="World") String name,
            Map<String, Object> model
    ) {
        model.put("name", name);

        return "greeting";
    }

    @GetMapping("/")
    public String main(Map<String, Object> model) {
        Iterable<Task> tasks = taskRepo.findAll();

        model.put("tasks", tasks);

        return "main";
    }

    @PostMapping("/")
    public String addTask(
            @RequestParam String number,
            @RequestParam String start,
            @RequestParam String end,
            Map<String, Object> model
    ) throws ParseException {

        Task task = new Task("auto", number, start, end);

        taskRepo.save(task);

        List<Task> tasks = taskRepo.findAll();

        model.put("tasks", tasks);

        return "main";
    }

}