package com.example.cinema.auth;

import org.springframework.security.core.GrantedAuthority;

public class UserRoleAuthority implements GrantedAuthority {

    private String userRole;

    public UserRoleAuthority(String userRole) {
        this.userRole = userRole;
    }

    @Override
    public String getAuthority() {
        return userRole;
    }

}
