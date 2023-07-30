package com.example.cinema.mapper;

import com.example.cinema.dto.UserCreationDto;
import com.example.cinema.dto.UserDto;
import com.example.cinema.model.User;

import java.util.ArrayList;
import java.util.List;

public final class UserMapper {

    public static User userCreationDtoToUser(UserCreationDto user) {
        return new User(
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getUsername(),
                user.getPassword(),
                user.getRole());
    }

    public static List<UserDto> usersToUserDtos(List<User> users) {
        List<UserDto> userDtos = new ArrayList<>();

        for (User user : users) {
            userDtos.add(new UserDto(user));
        }

        return userDtos;
    }

}
