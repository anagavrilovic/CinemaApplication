package com.example.cinema.controller;

import com.example.cinema.dto.LoginDto;
import com.example.cinema.dto.UserCreationDto;
import com.example.cinema.dto.UserDto;
import com.example.cinema.dto.UserLoginInfoDto;
import com.example.cinema.service.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public UserDto register(@Valid @RequestBody UserCreationDto user) {
        return new UserDto(authenticationService.register(user));
    }

    @PostMapping("login")
    public UserLoginInfoDto login(@Valid @RequestBody LoginDto dto) {
        UserLoginInfoDto info = authenticationService.login(dto.getEmail(), dto.getPassword());
        return info;
    }

}
