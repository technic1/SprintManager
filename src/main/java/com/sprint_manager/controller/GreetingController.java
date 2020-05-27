package com.sprint_manager.controller;

import com.sprint_manager.domain.Task;
import com.sprint_manager.repos.TaskRepo;
import com.sprint_manager.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class GreetingController {
    @Autowired
    private TaskRepo taskRepo;

    @Autowired
    private TaskService taskService;

    @GetMapping("/greeting")
    public String greeting(
            @RequestParam(name="name", required=false, defaultValue="World") String name,
            Map<String, Object> model
    ) {
        model.put("name", name);

        return "greeting";
    }

//    @GetMapping("/")
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String main(Map<String, Object> model) {
        Iterable<Task> tasks = taskRepo.findAll();

        model.put("tasks", tasks);

        return "main";
    }

    @RequestMapping(value = "/", method = RequestMethod.POST, params = "add")
    public String addTask(
            @RequestParam String title,
            @RequestParam String priority,
            @RequestParam String estimate,
            Map<String, Object> model
    ) throws ParseException {
        taskService.addTask(title, priority, estimate);

        List<Task> tasks = taskRepo.findAll();

        model.put("tasks", tasks);

        return "main";
    }

    @RequestMapping(value = "/", method = RequestMethod.POST, params = "delete")
    public String addTask(
            @RequestParam String id,
            Map<String, Object> model
    ) {
        System.out.println("deleting task");
        taskService.deleteTask(id);

        List<Task> tasks = taskRepo.findAll();

        model.put("tasks", tasks);

        return "main";
    }

}