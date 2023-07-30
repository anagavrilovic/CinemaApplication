package com.example.cinema.service;

import com.example.cinema.dto.UserCreationDto;
import com.example.cinema.model.User;

import java.util.List;

public interface UserService {

    List<User> findAllUsers();

    User save(UserCreationDto user);

    User findById(Long id);

}
