package com.example.cinema.controller;

import com.example.cinema.dto.UserDto;
import com.example.cinema.mapper.UserMapper;
import com.example.cinema.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserDto> getAll() {
        return UserMapper.usersToUserDtos(userService.findAllUsers());
    }

}
