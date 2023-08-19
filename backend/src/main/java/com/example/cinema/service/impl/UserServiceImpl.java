package com.example.cinema.service.impl;

import com.example.cinema.dto.UserCreationDto;
import com.example.cinema.exception.EntityNotFoundException;
import com.example.cinema.exception.ObjectAlreadyExistsException;
import com.example.cinema.mapper.UserMapper;
import com.example.cinema.model.User;
import com.example.cinema.repository.UserRepository;
import com.example.cinema.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) {
                return userRepository.findByEmail(username)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            }
        };
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public User save(UserCreationDto user, String hashedPassword) {
        validateUser(user);
        return userRepository.save(UserMapper.userCreationDtoToUser(user, hashedPassword));
    }

    @Override
    @Transactional(readOnly = true)
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("There is no user with this id."));
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Not found user with this email."));
    }

    private void validateUser(UserCreationDto user) {
        userRepository.findByEmail(user.getEmail())
                .ifPresent(u -> {
                    throw new ObjectAlreadyExistsException("User with this username already exists");
                });

        userRepository.findByUsername(user.getUsername())
                .ifPresent(u -> {
                    throw new ObjectAlreadyExistsException("User with this email already exists");
                });
    }
}
