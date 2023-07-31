package com.example.cinema.service;

import com.example.cinema.model.User;

public interface TokenService {
    String generateToken(User user);
}