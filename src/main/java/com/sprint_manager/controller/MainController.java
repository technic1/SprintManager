package com.sprint_manager.controller;

import com.sprint_manager.domain.*;
import com.sprint_manager.repos.SprintRepo;
import com.sprint_manager.repos.TaskRepo;
import com.sprint_manager.service.SprintService;
import com.sprint_manager.service.TaskService;
import com.sprint_manager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class MainController {
    @Autowired
    private TaskRepo taskRepo;

    @Autowired
    private TaskService taskService;

    @Autowired
    private SprintService sprintService;

    @Autowired
    private UserService userService;

    @Autowired
    private SprintRepo sprintRepo;


    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String main(
            Model model,
            @AuthenticationPrincipal User user
    ) {
        sprintService.getActiveSprintModel(model);
        sprintService.getBacklogModel(model, user);

        return "main";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addTask(
            @RequestParam String title,
            @RequestParam String priority,
            @RequestParam String estimate,
            @AuthenticationPrincipal User user,
            Model model
    ) {
        taskService.addTask(user, title, priority, estimate);
        sprintService.getActiveSprintModel(model);
        sprintService.getBacklogModel(model, user);

        return "main";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String deleteTask(
            @RequestParam String id,
            @RequestParam String authorName,
            @AuthenticationPrincipal User user,
            Model model
    ) {
        if (user.getRole() == UserRole.DEVELOPER) {
            if (user.getUsername().equals(authorName)) {
                taskService.deleteTask(id);
            } else {
                model.addAttribute("errorAccessDenied", "Access denied!");
            }
        } else if (user.getRole() == UserRole.ANALYST){
            taskService.deleteTask(id);
        }

        sprintService.getActiveSprintModel(model);
        sprintService.getBacklogModel(model, user);

        return "main";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String editTask(
            @RequestParam String id,
            @RequestParam String authorName,
            @RequestParam String title,
            @RequestParam String priority,
            @RequestParam String state,
            @RequestParam String estimate,
            @AuthenticationPrincipal User user,
            Model model
    ) {
        taskService.editTaskIfHaveAccess(model, user, authorName, id, title, priority, state, estimate);
        sprintService.getActiveSprintModel(model);
        sprintService.getBacklogModel(model, user);

        return "main";
    }

    @RequestMapping(value = "/close", method = RequestMethod.POST)
    public String closeTask(
            @RequestParam String id,
            @AuthenticationPrincipal User user,
            Model model
    ) {
        taskService.setTaskClosed(id);
        sprintService.getActiveSprintModel(model);
        sprintService.getBacklogModel(model, user);

        return "main";
    }

    @RequestMapping(value = "/delete-from-sprint", method = RequestMethod.POST)
    public String deleteTaskFromSprint(
            @RequestParam String taskId,
            @AuthenticationPrincipal User user,
            Model model
    ) {
        taskRepo.deleteTaskFromSprint(taskId);
        sprintService.getActiveSprintModel(model);
        sprintService.getBacklogModel(model, user);

        return "main";
    }


    @PostMapping("/registration/add")
    public String addUser (
            @RequestParam String username,
            @RequestParam String role,
            @RequestParam String password,
            Model model
    ) {
        if (!userService.addUser(username, role, password)) {
            model.addAttribute("errorUsernameAlreadyExists",
                    "Username already exists, choose another");
            return "registration";
        }

        return "redirect:/login";
    }
}