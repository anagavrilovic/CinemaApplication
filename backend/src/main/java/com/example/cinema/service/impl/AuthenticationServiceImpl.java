package com.example.cinema.service.impl;

import com.example.cinema.dto.UserCreationDto;
import com.example.cinema.dto.UserLoginInfoDto;
import com.example.cinema.exception.BadCredentialsException;
import com.example.cinema.model.User;
import com.example.cinema.service.AuthenticationService;
import com.example.cinema.service.TokenService;
import com.example.cinema.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserService userService;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationServiceImpl(UserService userService, TokenService tokenService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.tokenService = tokenService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public User register(UserCreationDto user) {
        return userService.save(user);
    }

    @Override
    public UserLoginInfoDto login(String email, String password) {
        User user = userService.findByEmail(email);
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("Password is incorrect");
        }

        return new UserLoginInfoDto(
                user.getEmail(),
                user.getUsername(),
                tokenService.generateToken(user),
                user.getRole()
        );
    }

}
