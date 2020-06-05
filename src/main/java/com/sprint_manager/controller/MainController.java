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

import java.util.List;

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

        Sprint activeSprint = sprintService.getActiveSprint();
        if (activeSprint != null) {
            List<Task> sprintTasks = taskRepo.getAllTasksBySprintId(activeSprint.getId().toString());

            model.addAttribute("sprintTasks", sprintTasks);
            model.addAttribute("sprint", activeSprint);
        }
        List<Task> tasks = taskRepo.getAllTasksFromBacklog();
        List<Sprint> sprints = sprintService.getAllSprints();

        model.addAttribute("user", user);
        model.addAttribute("sprints", sprints);
        model.addAttribute("tasks", tasks);

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
        Task task = new Task();

        task.setTitle(title);
        task.setTaskPriority(TaskPriority.valueOf(priority));
        task.setEstimate(Integer.valueOf(estimate));

        taskService.addTask(user, task);

        if (sprintService.getActiveSprint() != null) {
            Sprint sprint = sprintService.getActiveSprint();
            List<Task> sprintTasks = taskRepo.getAllTasksBySprintId(sprint.getId().toString());

            model.addAttribute("sprintTasks", sprintTasks);
            model.addAttribute("sprint", sprint);
        }
        List<Task> tasks = taskRepo.getAllTasksFromBacklog();
        List<Sprint> sprints = sprintService.getAllSprints();

        model.addAttribute("user", user);
        model.addAttribute("sprints", sprints);
        model.addAttribute("tasks", tasks);

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
        }

        if (sprintService.getActiveSprint() != null) {
            Sprint sprint = sprintService.getActiveSprint();
            List<Task> sprintTasks = taskRepo.getAllTasksBySprintId(sprint.getId().toString());

            model.addAttribute("sprintTasks", sprintTasks);
            model.addAttribute("sprint", sprint);
        }
        List<Task> tasks = taskRepo.getAllTasksFromBacklog();
        List<Sprint> sprints = sprintService.getAllSprints();

        model.addAttribute("user", user);
        model.addAttribute("sprints", sprints);
        model.addAttribute("tasks", tasks);

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
        if (user.getRole() == UserRole.DEVELOPER) {
            if (user.getUsername().equals(authorName)) {
                taskService.editTask(id, title, priority, state, estimate);
            } else {
                model.addAttribute("errorAccessDenied", "Access denied!");
            }
        }

        if (sprintService.getActiveSprint() != null) {
            Sprint sprint = sprintService.getActiveSprint();
            List<Task> sprintTasks = taskRepo.getAllTasksBySprintId(sprint.getId().toString());

            model.addAttribute("sprintTasks", sprintTasks);
            model.addAttribute("sprint", sprint);
        }

        List<Task> tasks = taskRepo.getAllTasksFromBacklog();
        List<Sprint> sprints = sprintService.getAllSprints();

        model.addAttribute("user", user);
        model.addAttribute("sprints", sprints);
        model.addAttribute("tasks", tasks);

        return "main";
    }

    @RequestMapping(value = "/close", method = RequestMethod.POST)
    public String closeTask(
            @RequestParam String id,
            @AuthenticationPrincipal User user,
            Model model
    ) {
        taskService.setTaskClosed(id);

        if (sprintService.getActiveSprint() != null) {
            Sprint sprint = sprintService.getActiveSprint();
            List<Task> sprintTasks = taskRepo.getAllTasksBySprintId(sprint.getId().toString());

            model.addAttribute("sprintTasks", sprintTasks);
            model.addAttribute("sprint", sprint);
        }
        List<Task> tasks = taskRepo.getAllTasksFromBacklog();
        List<Sprint> sprints = sprintService.getAllSprints();

        model.addAttribute("user", user);
        model.addAttribute("sprints", sprints);
        model.addAttribute("tasks", tasks);

        return "main";
    }

    @RequestMapping(value = "/delete-from-sprint", method = RequestMethod.POST)
    public String deleteTaskFromSprint(
            @RequestParam String taskId,
            @AuthenticationPrincipal User user,
            Model model
    ) {
        taskRepo.deleteTaskFromSprint(taskId);

        Sprint sprint = sprintService.getActiveSprint();
        if (sprint != null) {
            List<Task> sprintTasks = taskRepo.getAllTasksBySprintId(sprint.getId().toString());

            model.addAttribute("sprintTasks", sprintTasks);
            model.addAttribute("sprint", sprint);
        }

        List<Task> tasks = taskRepo.getAllTasksFromBacklog();
        List<Sprint> sprints = sprintService.getAllSprints();

        model.addAttribute("user", user);
        model.addAttribute("sprints", sprints);
        model.addAttribute("tasks", tasks);

        return "main";
    }

    @RequestMapping(value = "/open", method = RequestMethod.POST)
    public String openTask(
            @RequestParam String id,
            @AuthenticationPrincipal User user,
            Model model
    ) {
        taskService.setTaskOpen(id);

        if (sprintService.getActiveSprint() != null) {
            Sprint sprint = sprintService.getActiveSprint();
            List<Task> sprintTasks = taskRepo.getAllTasksBySprintId(sprint.getId().toString());

            model.addAttribute("sprintTasks", sprintTasks);
            model.addAttribute("sprint", sprint);
        }
        List<Task> tasks = taskRepo.getAllTasksFromBacklog();
        List<Sprint> sprints = sprintService.getAllSprints();

        model.addAttribute("user", user);
        model.addAttribute("sprints", sprints);
        model.addAttribute("tasks", tasks);

        return "main";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration() {

        return "registration";
    }

    @PostMapping("/registration")
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