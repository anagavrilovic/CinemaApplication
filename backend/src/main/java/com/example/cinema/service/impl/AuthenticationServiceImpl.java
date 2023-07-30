package com.example.cinema.service.impl;

import com.example.cinema.dto.UserCreationDto;
import com.example.cinema.model.User;
import com.example.cinema.service.AuthenticationService;
import com.example.cinema.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserService userService;

    public AuthenticationServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    @Transactional
    public User register(UserCreationDto user) {
        return userService.save(user);
    }


}
