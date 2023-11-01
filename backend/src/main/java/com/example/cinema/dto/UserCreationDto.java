package com.example.cinema.dto;

import com.example.cinema.model.User;
import com.example.cinema.model.enums.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class UserCreationDto {

    @NotBlank(message = "First name is mandatory.")
    private String firstName;

    @NotBlank(message = "Projection id is mandatory.")
    private String lastName;

    @NotNull(message = "Email is mandatory.")
    @Email(message = "Wrong email format.")
    private String email;

    @NotBlank(message = "Username is mandatory.")
    private String username;

    @NotBlank(message = "Password is mandatory.")
    private String password;

    @NotNull(message = "Role is mandatory.")
    private UserRole role;

    public UserCreationDto() {}

    public UserCreationDto(String firstName, String lastName, String email, String username, String password, UserRole role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public UserCreationDto(User user) {
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.username = user.getUserUsername();
        this.password = user.getPassword();
        this.role = user.getRole();
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public UserRole getRole() {
        return role;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
}
