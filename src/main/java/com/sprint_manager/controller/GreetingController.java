package com.sprint_manager.controller;

import com.sprint_manager.domain.Sprint;
import com.sprint_manager.domain.Task;
import com.sprint_manager.repos.SprintRepo;
import com.sprint_manager.repos.TaskRepo;
import com.sprint_manager.service.SprintService;
import com.sprint_manager.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

@Controller
public class GreetingController {
    @Autowired
    private TaskRepo taskRepo;

    @Autowired
    private TaskService taskService;

    @Autowired
    private SprintService sprintService;

    @Autowired
    private SprintRepo sprintRepo;

    @GetMapping("/greeting")
    public String greeting(
            @RequestParam(name="name", required=false, defaultValue="World") String name,
            Map<String, Object> model
    ) {
        model.put("name", name);

        return "greeting";
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String main(Map<String, Object> model) {
        List<Task> tasks = taskRepo.getAllTasksFromBacklog();
        List<Sprint> sprints = sprintService.getAllSprints();

        model.put("sprints", sprints);
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


        List<Task> tasks = taskRepo.getAllTasksFromBacklog();
        List<Sprint> sprints = sprintService.getAllSprints();

        model.put("sprints", sprints);
        model.put("tasks", tasks);

        return "main";
    }

    @RequestMapping(value = "/", method = RequestMethod.POST, params = "delete")
    public String deleteTask(
            @RequestParam String id,
            Map<String, Object> model
    ) {
        taskService.deleteTask(id);

        List<Task> tasks = taskRepo.getAllTasksFromBacklog();
        List<Sprint> sprints = sprintService.getAllSprints();

        model.put("sprints", sprints);
        model.put("tasks", tasks);

        return "main";
    }

    @RequestMapping(value = "/", method = RequestMethod.POST, params = "edit")
    public String addTask(
            @RequestParam String id,
            @RequestParam String title,
            @RequestParam String priority,
            @RequestParam String state,
            @RequestParam String estimate,
            Map<String, Object> model
    ) throws ParseException {
        taskService.editTask(id, title, priority, state, estimate);

        List<Task> tasks = taskRepo.getAllTasksFromBacklog();
        List<Sprint> sprints = sprintService.getAllSprints();

        model.put("sprints", sprints);
        model.put("tasks", tasks);

        return "main";
    }

}