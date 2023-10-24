package com.example.cinema.constants;

import com.example.cinema.dto.UserDto;
import com.example.cinema.model.User;

public class UserConstants {

    public static User getSimpleUser(Long id, String name) {
        User user = new User();
        user.setId(id);
        user.setUsername(name);

        return user;
    }

    public static UserDto getSimpleUserDto(Long id, String name) {
        UserDto userDto = new UserDto();
        userDto.setId(id);
        userDto.setUsername(name);

        return userDto;
    }

}
