package com.example.cinema.constants;

import com.example.cinema.dto.UserDto;
import com.example.cinema.model.User;

public class UserConstants {

    public static final Integer DB_COUNT = 4;
    public static final Long DB_ID = 1L;
    public static final String DB_EMAIL = "admin@gmail.com";
    public static final String DB_FIRST_NAME = "Admin";
    public static final String DB_LAST_NAME = "Admin";


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
