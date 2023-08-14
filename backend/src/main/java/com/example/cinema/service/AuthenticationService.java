package com.example.cinema.service;

import com.example.cinema.dto.UserCreationDto;
import com.example.cinema.dto.UserLoginInfoDto;
import com.example.cinema.model.User;

public interface AuthenticationService {

    User register(UserCreationDto user);

    UserLoginInfoDto login(String email, String password);

}
