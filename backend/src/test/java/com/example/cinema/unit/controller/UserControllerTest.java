package com.example.cinema.unit.controller;

import com.example.cinema.arguments_provider.IdAndNameArgumentsProvider;
import com.example.cinema.constants.MovieConstants;
import com.example.cinema.constants.UserConstants;
import com.example.cinema.controller.UserController;
import com.example.cinema.dto.MovieDto;
import com.example.cinema.dto.UserDto;
import com.example.cinema.model.Movie;
import com.example.cinema.model.User;
import com.example.cinema.service.UserService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserService mockUserService;

    @InjectMocks
    private UserController userController;

    @ParameterizedTest
    @ArgumentsSource(IdAndNameArgumentsProvider.class)
    void Should_ListAllUsers(Long id, String name) {
        User user = UserConstants.getSimpleUser(id, name);

        UserDto userDto = UserConstants.getSimpleUserDto(id, name);

        List<User> users = List.of(user);
        List<UserDto> expected = List.of(userDto);

        when(mockUserService.findAllUsers()).thenReturn(users);

        List<UserDto> actual = userController.getAll();

        assertAll(
                () -> assertNotNull(actual),
                () -> assertEquals(expected.size(), actual.size()),
                () -> assertEquals(expected.get(0).getId(), actual.get(0).getId()),
                () -> assertEquals(expected.get(0).getUsername(), actual.get(0).getUsername())
        );
    }

}
