package com.example.cinema.unit.service;

import com.example.cinema.arguments_provider.IdArgumentsProvider;
import com.example.cinema.arguments_provider.IdAndNameArgumentsProvider;
import com.example.cinema.exception.EntityNotFoundException;
import com.example.cinema.model.Theater;
import com.example.cinema.repository.TheaterRepository;
import com.example.cinema.service.impl.TheaterServiceImpl;
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
public class TheaterServiceTest {

    @Mock
    TheaterRepository mockTheaterRepository;

    @InjectMocks
    TheaterServiceImpl theaterService;

    @ParameterizedTest
    @ArgumentsSource(IdAndNameArgumentsProvider.class)
    void Should_ReturnTheaterList(Long theaterId, String theaterName) {
        Theater theater = new Theater();
        theater.setId(theaterId);
        theater.setName(theaterName);

        List<Theater> theatersExpected = Collections.singletonList(theater);

        when(mockTheaterRepository.findAll()).thenReturn(theatersExpected);

        List<Theater> actual = theaterService.findAll();

        assertAll(
                () -> assertNotNull(actual),
                () -> assertEquals(actual.size(), theatersExpected.size()),
                () -> assertEquals(actual.get(0).getName(), theatersExpected.get(0).getName()),
                () -> assertEquals(actual.get(0).getId(), theatersExpected.get(0).getId())
        );
    }

    @ParameterizedTest
    @ArgumentsSource(IdArgumentsProvider.class)
    void Should_ReturnTheaterWithGivenId_WhenTheaterExists(Long id) {
        Theater expected = new Theater();
        expected.setId(id);

        when(mockTheaterRepository.findById(any())).thenReturn(Optional.of(expected));

        Theater actual = theaterService.findById(id);

        assertAll(
                () -> assertNotNull(actual),
                () -> assertEquals(actual.getId(), expected.getId())
        );
    }

    @ParameterizedTest
    @ArgumentsSource(IdArgumentsProvider.class)
    void Should_ThrowEntityNotFoundException_WhenTheaterNotExists(Long id) {
        when(mockTheaterRepository.findById(any())).thenReturn(Optional.empty());

        Executable executable = () -> theaterService.findById(id);

        assertThrows(EntityNotFoundException.class, executable);
    }
    
}
