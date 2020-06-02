package com.sprint_manager.controller;

import com.sprint_manager.domain.Sprint;
import com.sprint_manager.domain.Task;
import com.sprint_manager.domain.User;
import com.sprint_manager.repos.TaskRepo;
import com.sprint_manager.service.SprintService;
import com.sprint_manager.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.util.HashMap;
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

    Map<String, Object> errors = new HashMap<>();

    @RequestMapping(value = "/sprint/{sprintId}", method = RequestMethod.GET)
    public String main(
            @PathVariable String sprintId,
            @AuthenticationPrincipal User user,
            Model model
    ) {

        model.addAllAttributes(errors);
        errors.clear();

        Integer integer = Integer.valueOf(sprintId);
        List<Task> sprintTasks = taskRepo.getAllTasksBySprintId(integer);
        Sprint sprint = sprintService.getSprintById(integer);
        List<Task> backlogTasks = taskRepo.getAllTasksFromBacklog();
        List<Sprint> sprints = sprintService.getAllSprints();

        model.addAttribute("user", user);
        model.addAttribute("sprints", sprints);
        model.addAttribute("sprint", sprint);
        model.addAttribute("sprintTasks", sprintTasks);
        model.addAttribute("backlogTasks", backlogTasks);

        return "sprint";
    }

    @RequestMapping(value = "/sprint/{sprintId}/delete", method = RequestMethod.POST)
    public String deleteTask(
            @PathVariable String sprintId,
            @RequestParam String taskId,
            Model model
    ) {
        Integer taskIdInt = Integer.valueOf(taskId);
        taskRepo.deleteTaskFromSprint(taskIdInt);

        return "redirect:/sprint/{sprintId}";
    }

    @RequestMapping(value = "/sprint/{sprintId}/edit", method = RequestMethod.POST)
    public String addTask(
            @PathVariable String sprintId,
            @RequestParam String id,
            @RequestParam String title,
            @RequestParam String priority,
            @RequestParam String state,
            @RequestParam String estimate,
            Model model
    ) throws ParseException {
        taskService.editTask(id, title, priority, state, estimate);

        return "redirect:/sprint/{sprintId}";
    }

    @RequestMapping(value = "/sprint/{sprintId}/add", method = RequestMethod.POST)
    public String addTaskToSprint(
            @PathVariable String sprintId,
            @RequestParam String taskId,
            Model model
    ) {

        sprintService.addTaskToSprint(sprintId, taskId);

        Integer integer = Integer.valueOf(sprintId);


        return "redirect:/sprint/{sprintId}";
    }

    @RequestMapping(value = "/sprint/{sprintId}/start", method = RequestMethod.POST)
    public String startSprint(
            @PathVariable String sprintId,
            Model model
    ) {
        Integer id = Integer.valueOf(sprintId);

        boolean haveActiveSprint = sprintService.haveActiveSprint();
        boolean haveTasks = sprintService.haveTasks(id);
        if (haveActiveSprint) {
            errors.put("errorOneActiveSprint", "Can't be active, close existing active sprint");
        }
        if (!haveTasks) {
            errors.put("errorNoTasksInSprint", "Can't be started, add some tasks in sprint");
        }
        if (!haveActiveSprint && haveTasks) {
            sprintService.startSprint(id);
        }

        return "redirect:/sprint/{sprintId}";
    }

    @RequestMapping(value = "/sprint/{sprintId}/finish", method = RequestMethod.POST)
    public String finishSprint(
            @PathVariable String sprintId,
            Model model
    ) {
        Integer integer = Integer.valueOf(sprintId);

        if (!sprintService.haveOpenTasksBySprintId(integer)) {
            sprintService.finishSprint(integer);
        } else {
            errors.put("errorSetFinished", "Can't be finished, close existing opened tasks or put them in backlog");
        }

        return "redirect:/sprint/{sprintId}";
    }

    @RequestMapping(value = "/newsprint", method = RequestMethod.GET)
    public String newSprint(
            @AuthenticationPrincipal User user,
            Model model
    ) {
        List<Sprint> sprints = sprintService.getAllSprints();

        model.addAttribute("user", user);
        model.addAttribute("sprints", sprints);
        return "new_sprint";
    }

    @RequestMapping(value = "/newsprint", method = RequestMethod.POST)
    public String addSprint(
            @RequestParam String title,
            @RequestParam String startDate,
            @RequestParam String endDateExpect,
            @AuthenticationPrincipal User user,
            Model model
    ) throws ParseException {

        Sprint sprint = sprintService.insertNewSprint(user, title, startDate, endDateExpect);
        String url = "sprint/" + sprint.getId();
        List<Sprint> sprints = sprintService.getAllSprints();


        model.addAttribute("sprints", sprints);

        return "redirect:" + url;
    }
}
