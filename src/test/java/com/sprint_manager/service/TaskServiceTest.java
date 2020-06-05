package com.sprint_manager.service;

import com.sprint_manager.domain.Task;
import com.sprint_manager.domain.TaskState;
import com.sprint_manager.domain.User;
import com.sprint_manager.repos.TaskRepo;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

//@RunWith(SpringRunner.class)
@SpringBootTest
class TaskServiceTest {
    @Autowired
    private TaskService taskService;

    @MockBean
    private TaskRepo taskRepo;

    @Test
    void addTask() {
        Task task = new Task();
        User user = new User();
        user.setId(1);

        boolean isTaskCreated = taskService.addTask(user, task);

        Assert.assertNotNull(task.getStartDate());
        Assert.assertNotNull(task.getAuthorId());
        Assert.assertEquals(TaskState.OPEN.toString(), task.getTaskState());
        Assert.assertTrue(isTaskCreated);

    }
}