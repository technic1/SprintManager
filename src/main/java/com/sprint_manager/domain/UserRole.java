package com.sprint_manager.domain;


import org.springframework.security.core.GrantedAuthority;

public enum UserRole implements GrantedAuthority {
    DEVELOPER, MANAGER, ANALYST, ADMIN;


    @Override
    public String getAuthority() {
        return name();
    }
}
