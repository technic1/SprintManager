package com.sprint_manager.service;

import com.sprint_manager.domain.Task;
import com.sprint_manager.domain.TaskState;
import com.sprint_manager.domain.User;
import com.sprint_manager.repos.TaskRepo;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class TaskServiceTest {
    @Autowired
    private TaskService taskService;

    @MockBean
    private TaskRepo taskRepo;

    @Test
    void addTask() {
        User user = new User();
        user.setId(1);

        String title = "task 1";
        String priority = "HIGH";
        String estimate = "2";

        Task task = Mockito.mock(Task.class);

        boolean isTaskCreated = taskService.addTask(user, title, priority, estimate);
        Assert.assertEquals(task.getTitle(), title);
        Assert.assertEquals(task.getTaskPriority(), priority);
        Assert.assertEquals(task.getEstimate(), Integer.valueOf(estimate));

        Mockito.when(taskService.addTask(user, title, priority, estimate));
        Mockito.verify(taskRepo, Mockito.times(1)).createTask(ArgumentMatchers.any(Task.class));
    }
}