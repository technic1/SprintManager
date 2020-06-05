package com.sprint_manager.service;

import com.sprint_manager.domain.User;
import com.sprint_manager.domain.UserRole;
import com.sprint_manager.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User usersFromDb = userRepo.findByUserName(username);

        if (usersFromDb == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return usersFromDb;
    }

    public boolean addUser(String username, String role, String password) {
        User usersFromDb = userRepo.findByUserName(username);

        if (usersFromDb != null) {
            return false;
        }
        User user = new User();

        Set<UserRole> roles = new HashSet<>();
        roles.add(UserRole.valueOf(role));
        user.setRoles(roles);

        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));

        userRepo.saveUser(user);

        return true;
    }
}
