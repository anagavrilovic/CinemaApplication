package com.example.cinema.unit.service;

import com.example.cinema.arguments_provider.EmailArgumentsProvider;
import com.example.cinema.arguments_provider.IdAndNameAndEmailArgumentsProvider;
import com.example.cinema.arguments_provider.IdArgumentsProvider;
import com.example.cinema.arguments_provider.IdAndNameArgumentsProvider;
import com.example.cinema.dto.UserCreationDto;
import com.example.cinema.exception.EntityNotFoundException;
import com.example.cinema.exception.ObjectAlreadyExistsException;
import com.example.cinema.model.User;
import com.example.cinema.repository.UserRepository;
import com.example.cinema.service.impl.UserServiceImpl;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    UserRepository mockUserRepository;

    @InjectMocks
    UserServiceImpl userService;

    @ParameterizedTest
    @ArgumentsSource(IdAndNameArgumentsProvider.class)
    void Should_ReturnUserList(Long userId, String username) {
        User User = new User();
        User.setId(userId);
        User.setUsername(username);

        List<User> usersExpected = Collections.singletonList(User);

        when(mockUserRepository.findAll()).thenReturn(usersExpected);

        List<User> actual = userService.findAllUsers();

        assertAll(
                () -> assertNotNull(actual),
                () -> assertEquals(actual.size(), usersExpected.size()),
                () -> assertEquals(actual.get(0).getUserUsername(), usersExpected.get(0).getUserUsername()),
                () -> assertEquals(actual.get(0).getId(), usersExpected.get(0).getId())
        );
    }

    @ParameterizedTest
    @ArgumentsSource(IdAndNameAndEmailArgumentsProvider.class)
    void Should_CreateNewUser_When_UserWithUsernameAndEmailNotAlreadyExists(Long userId, String username, String email) {
        UserCreationDto request = new UserCreationDto();
        request.setUsername(username);
        request.setEmail(email);

        User expected = new User();
        expected.setId(userId);
        expected.setUsername(username);
        expected.setEmail(email);

        when(mockUserRepository.findByEmail(any())).thenReturn(Optional.empty());
        when(mockUserRepository.findByUsername(any())).thenReturn(Optional.empty());
        when(mockUserRepository.save(any())).thenReturn(expected);

        User actual = userService.save(request, null);

        assertAll(
                () -> assertNotNull(actual),
                () -> assertEquals(expected.getId(), actual.getId()),
                () -> assertEquals(expected.getUserUsername(), actual.getUserUsername()),
                () -> assertEquals(expected.getEmail(), actual.getEmail())
        );
    }

    @ParameterizedTest
    @ArgumentsSource(IdAndNameAndEmailArgumentsProvider.class)
    void Should_ThrowObjectAlreadyExistsException_When_UsernameAlreadyExists(Long userId, String username, String email) {
        UserCreationDto request = new UserCreationDto();
        request.setUsername(username);
        request.setEmail(email);

        User expected = new User();
        expected.setId(userId);
        expected.setUsername(username);
        expected.setEmail(email);

        when(mockUserRepository.findByEmail(any())).thenReturn(Optional.of(expected));

        Executable executable = () -> userService.save(request, null);

        assertThrows(ObjectAlreadyExistsException.class, executable);
    }

    @ParameterizedTest
    @ArgumentsSource(IdAndNameAndEmailArgumentsProvider.class)
    void Should_ThrowObjectAlreadyExistsException_When_UserWithEmailAlreadyExists(Long userId, String username, String email) {
        UserCreationDto request = new UserCreationDto();
        request.setUsername(username);
        request.setEmail(email);

        User expected = new User();
        expected.setId(userId);
        expected.setUsername(username);
        expected.setEmail(email);

        when(mockUserRepository.findByUsername(any())).thenReturn(Optional.of(expected));

        Executable executable = () -> userService.save(request, null);

        assertThrows(ObjectAlreadyExistsException.class, executable);
    }

    @ParameterizedTest
    @ArgumentsSource(IdArgumentsProvider.class)
    void Should_ReturnUserWithGivenId_WhenUserExists(Long id) {
        User expected = new User();
        expected.setId(id);

        when(mockUserRepository.findById(any())).thenReturn(Optional.of(expected));

        User actual = userService.findById(id);

        assertAll(
                () -> assertNotNull(actual),
                () -> assertEquals(actual.getId(), expected.getId())
        );
    }

    @ParameterizedTest
    @ArgumentsSource(IdArgumentsProvider.class)
    void Should_ThrowEntityNotFoundException_WhenUserNotExists(Long id) {
        when(mockUserRepository.findById(any())).thenReturn(Optional.empty());

        Executable executable = () -> userService.findById(id);

        assertThrows(EntityNotFoundException.class, executable);
    }

    @ParameterizedTest
    @ArgumentsSource(EmailArgumentsProvider.class)
    void Should_ReturnUserWithGivenEmail_WhenUserExists(String email) {
        User expected = new User();
        expected.setEmail(email);

        when(mockUserRepository.findByEmail(any())).thenReturn(Optional.of(expected));

        User actual = userService.findByEmail(email);

        assertAll(
                () -> assertNotNull(actual),
                () -> assertEquals(actual.getEmail(), expected.getEmail())
        );
    }

    @ParameterizedTest
    @ArgumentsSource(EmailArgumentsProvider.class)
    void Should_ThrowEntityNotFoundException_WhenUserWithGivenEmailNotExists(String email) {
        when(mockUserRepository.findByEmail(any())).thenReturn(Optional.empty());

        Executable executable = () -> userService.findByEmail(email);

        assertThrows(EntityNotFoundException.class, executable);
    }
    
}
