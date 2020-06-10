package com.sprint_manager.service;

import com.sprint_manager.domain.User;
import com.sprint_manager.domain.UserRole;
import com.sprint_manager.repos.UserRepo;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserServiceTest {
    @Autowired
    private UserService userService;

    @MockBean
    private UserRepo userRepo;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Test
    void addUser() {
        String username = "new user";
        String role = UserRole.DEVELOPER.toString();
        String password = "123";

        when(userRepo.findByUserName("new user")).thenReturn(null);
        User user = mock(User.class);

        boolean isUserCreated = userService.addUser(username, role, password);

        verify(userRepo, times(1)).saveUser(any(User.class));
        Assert.assertTrue(isUserCreated);
    }

    @Test
    void addUserFail() {
        String username = "user";
        String role = UserRole.DEVELOPER.toString();
        String password = "123";

        when(userRepo.findByUserName("user")).thenReturn(new User());

        boolean isUserCreated = userService.addUser(username, role, password);
        ArgumentCaptor
        verify(userRepo, times(0)).saveUser(any(User.class));
        Assert.assertFalse(isUserCreated);
    }
}