package com.example.cinema.service;

import com.example.cinema.dto.UserCreationDto;
import com.example.cinema.model.User;

public interface AuthenticationService {

    User register(UserCreationDto user);

    String login(String email, String password);

}
