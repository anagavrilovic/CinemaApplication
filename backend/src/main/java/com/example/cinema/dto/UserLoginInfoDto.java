package com.example.cinema.dto;

import com.example.cinema.model.enums.UserRole;

public class UserLoginInfoDto {

    private String email;

    private String username;
    private String accessToken;
    private UserRole role;

    public UserLoginInfoDto() {
    }

    public UserLoginInfoDto(String email, String username, String accessToken, UserRole role) {
        this.email = email;
        this.username = username;
        this.accessToken = accessToken;
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public UserRole getRole() {
        return role;
    }

    @Override
    public String toString() {
        return "UserLoginInfoDto{" +
                "email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", accessToken='" + accessToken + '\'' +
                ", role=" + role +
                '}';
    }

}
