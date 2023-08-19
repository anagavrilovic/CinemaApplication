package com.example.cinema.service.impl;

import com.example.cinema.dto.UserCreationDto;
import com.example.cinema.dto.UserLoginInfoDto;
import com.example.cinema.exception.BadCredentialsException;
import com.example.cinema.model.User;
import com.example.cinema.service.AuthenticationService;
import com.example.cinema.service.JwtService;
import com.example.cinema.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthenticationServiceImpl(UserService userService, JwtService jwtService, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    @Override
    @Transactional
    public User register(UserCreationDto user) {
        return userService.save(user, passwordEncoder.encode(user.getPassword()));
    }

    @Override
    public UserLoginInfoDto login(String email, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));

        User user = userService.findByEmail(email);
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("Password is incorrect");
        }

        return new UserLoginInfoDto(
                user.getEmail(),
                user.getUsername(),
                jwtService.generateToken(user),
                user.getRole()
        );
    }

}
