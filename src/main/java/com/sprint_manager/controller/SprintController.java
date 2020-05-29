package com.sprint_manager.controller;

import com.sprint_manager.domain.Sprint;
import com.sprint_manager.domain.Task;
import com.sprint_manager.repos.TaskRepo;
import com.sprint_manager.service.SprintService;
import com.sprint_manager.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class SprintController {
    @Autowired
    private TaskRepo taskRepo;

    @Autowired
    private TaskService taskService;

    @Autowired
    private SprintService sprintService;

    @RequestMapping(value = "/sprint/{sprintId}", method = RequestMethod.GET)
    public String main(Map<String, Object> model,
                       @PathVariable String sprintId) {

        Integer integer = Integer.valueOf(sprintId);
        List<Task> sprintTasks = taskRepo.getAllTasksBySprintId(integer);
        Sprint sprint = sprintService.getSprintById(integer);
        List<Task> backlogTasks = taskRepo.getAllTasksFromBacklog();

        model.put("sprint", sprint);
        model.put("sprintTasks", sprintTasks);
        model.put("backlogTasks", backlogTasks);

        return "sprint";
    }

    @RequestMapping(value = "/sprint/{sprintId}", method = RequestMethod.POST, params = "delete")
    public String deleteTask(
            @PathVariable String sprintId,
            @RequestParam String taskId,
            Map<String, Object> model
    ) {
        Integer taskIdInt = Integer.valueOf(taskId);
        taskRepo.deleteTaskFromSprint(taskIdInt);

        Integer integer = Integer.valueOf(sprintId);
        List<Task> sprintTasks = taskRepo.getAllTasksBySprintId(integer);
        Sprint sprint = sprintService.getSprintById(integer);
        List<Task> backlogTasks = taskRepo.getAllTasksFromBacklog();

        model.put("sprint", sprint);
        model.put("sprintTasks", sprintTasks);
        model.put("backlogTasks", backlogTasks);

        return "sprint";
    }

    @RequestMapping(value = "/sprint/{sprintId}", method = RequestMethod.POST, params = "edit")
    public String addTask(
            @PathVariable String sprintId,
            @RequestParam String id,
            @RequestParam String title,
            @RequestParam String priority,
            @RequestParam String state,
            @RequestParam String estimate,
            Map<String, Object> model
    ) throws ParseException {
        taskService.editTask(id, title, priority, state, estimate);

        Integer integer = Integer.valueOf(sprintId);
        List<Task> sprintTasks = taskRepo.getAllTasksBySprintId(integer);
        Sprint sprint = sprintService.getSprintById(integer);
        List<Task> backlogTasks = taskRepo.getAllTasksFromBacklog();

        model.put("sprint", sprint);
        model.put("sprintTasks", sprintTasks);
        model.put("backlogTasks", backlogTasks);

        return "sprint";
    }

    @RequestMapping(value = "/sprint/{sprintId}", method = RequestMethod.POST, params = "add")
    public String addTaskToSprint(
            @PathVariable String sprintId,
            @RequestParam String taskId,
            Map<String, Object> model
    ) {

        sprintService.addTaskToSprint(sprintId, taskId);

        Integer integer = Integer.valueOf(sprintId);

        List<Task> sprintTasks = taskRepo.getAllTasksBySprintId(integer);
        Sprint sprint = sprintService.getSprintById(integer);
        List<Task> backlogTasks = taskRepo.getAllTasksFromBacklog();

        model.put("sprint", sprint);
        model.put("sprintTasks", sprintTasks);
        model.put("backlogTasks", backlogTasks);

        return "sprint";
    }

    @RequestMapping(value = "/newsprint", method = RequestMethod.GET)
    public String newSprint(
            Map<String, Object> model
    ) {

        return "new_sprint";
    }

    @RequestMapping(value = "/newsprint", method = RequestMethod.POST)
    public String addSprint(
            @RequestParam String title,
            @RequestParam String startDate,
            @RequestParam String endDateExpect,
            Map<String, Object> model
    ) throws ParseException {

        Sprint sprint = sprintService.insertNewTask(title, startDate, endDateExpect);
        String url = "sprint/" + sprint.getId();

        return "redirect:" + url;
    }
}
