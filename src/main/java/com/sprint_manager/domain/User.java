package com.sprint_manager.domain;

import java.util.Set;


public class User {

    private Long id;

    private String username;
    private String password;



    private Set<UserRole> roles;

    public User() {

    }

    public User(String username, String password, Set<UserRole> roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<UserRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<UserRole> role) {
        this.roles = role;
    }
}
