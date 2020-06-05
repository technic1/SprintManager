package com.sprint_manager.controller;

import com.sprint_manager.domain.Sprint;
import com.sprint_manager.domain.Task;
import com.sprint_manager.domain.User;
import com.sprint_manager.domain.UserRole;
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

import java.util.List;

@Controller
public class SprintController {
    @Autowired
    private TaskRepo taskRepo;

    @Autowired
    private TaskService taskService;

    @Autowired
    private SprintService sprintService;

    @RequestMapping(value = "/sprint/{sprintId}", method = RequestMethod.GET)
    public String main(
            @PathVariable String sprintId,
            @AuthenticationPrincipal User user,
            Model model
    ) {
        sprintService.getSprintModel(model, sprintId);
        sprintService.getBacklogModel(model, user);

        return "sprint";
    }

    @RequestMapping(value = "/sprint/{sprintId}/delete", method = RequestMethod.POST)
    public String deleteTask(
            @PathVariable String sprintId,
            @RequestParam String taskId,
            @AuthenticationPrincipal User user,
            Model model
    ) {
        taskRepo.deleteTaskFromSprint(taskId);
        sprintService.getSprintModel(model, sprintId);
        sprintService.getBacklogModel(model, user);

        return "sprint";
    }

    @RequestMapping(value = "/sprint/{sprintId}/edit", method = RequestMethod.POST)
    public String addTask(
            @PathVariable String sprintId,
            @RequestParam String id,
            @RequestParam String authorName,
            @RequestParam String title,
            @RequestParam String priority,
            @RequestParam String state,
            @RequestParam String estimate,
            @AuthenticationPrincipal User user,
            Model model
    ) {
        taskService.editTaskIfDeveloperHaveAccess(model, user, authorName, id, title, priority, state, estimate);
        sprintService.getSprintModel(model, sprintId);
        sprintService.getBacklogModel(model, user);

        return "sprint";
    }

    @RequestMapping(value = "/sprint/{sprintId}/add", method = RequestMethod.POST)
    public String addTaskToSprint(
            @PathVariable String sprintId,
            @RequestParam String taskId,
            @AuthenticationPrincipal User user,
            Model model
    ) {
        sprintService.addTaskToSprint(sprintId, taskId);
        sprintService.getSprintModel(model, sprintId);
        sprintService.getBacklogModel(model, user);

        return "sprint";
    }

    @RequestMapping(value = "/sprint/{sprintId}/start", method = RequestMethod.POST)
    public String startSprint(
            @PathVariable String sprintId,
            @AuthenticationPrincipal User user,
            Model model
    ) {

        boolean haveActiveSprint = sprintService.haveActiveSprint();
        boolean haveTasks = sprintService.haveTasks(sprintId);
        if (haveActiveSprint) {
            model.addAttribute("errorOneActiveSprint", "Can't be active, close existing active sprint");
        }
        if (!haveTasks) {
            model.addAttribute("errorNoTasksInSprint", "Can't be started, add some tasks in sprint");
        }
        if (!haveActiveSprint && haveTasks) {
            sprintService.startSprint(sprintId);
        }

        sprintService.getSprintModel(model, sprintId);
        sprintService.getBacklogModel(model, user);
        return "sprint";
    }

    @RequestMapping(value = "/sprint/{sprintId}/finish", method = RequestMethod.POST)
    public String finishSprint(
            @PathVariable String sprintId,
            @AuthenticationPrincipal User user,
            Model model
    ) {
        if (!sprintService.haveOpenTasksBySprintId(sprintId)) {
            sprintService.finishSprint(sprintId);
        } else {
            model.addAttribute("errorSetFinished",
                    "Can't be finished, close existing opened tasks or put them in backlog");
        }

        sprintService.getSprintModel(model, sprintId);
        sprintService.getBacklogModel(model, user);

        return "sprint";
    }

    @RequestMapping(value = "/sprint/{sprintId}/edit-sprint", method = RequestMethod.POST)
    public String editSprint(
            @PathVariable String sprintId,
            @RequestParam String title,
            @RequestParam String startDate,
            @RequestParam String endDateExpect,
            @AuthenticationPrincipal User user,
            Model model
    ) {
        try {
            sprintService.editSprint(sprintId, title, startDate, endDateExpect);
        } catch (RuntimeException e) {
            model.addAttribute("errorDate", e.getMessage());
        }
        sprintService.getSprintModel(model, sprintId);
        sprintService.getBacklogModel(model, user);

        return "sprint";
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

    @RequestMapping(value = "/newsprint/new", method = RequestMethod.POST)
    public String addSprint(
            @RequestParam String title,
            @RequestParam String startDate,
            @RequestParam String endDateExpect,
            @AuthenticationPrincipal User user,
            Model model
    ) {
        List<Sprint> sprints = sprintService.getAllSprints();
        model.addAttribute("sprints", sprints);

        Sprint sprint = null;
        try {
            sprint = sprintService.insertNewSprint(user, title, startDate, endDateExpect);
        } catch (RuntimeException e) {
            model.addAttribute("errorDate", e.getMessage());
            return "newsprint";
        }

        String url = "sprint/" + sprint.getId();

        return "redirect:" + url;
    }
}
