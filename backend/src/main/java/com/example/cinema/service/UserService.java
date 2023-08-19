package com.example.cinema.service;

import com.example.cinema.dto.UserCreationDto;
import com.example.cinema.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService {

    UserDetailsService userDetailsService();

    List<User> findAllUsers();

    User save(UserCreationDto user, String hashedPassword);

    User findById(Long id);

    User findByEmail(String email);

}
