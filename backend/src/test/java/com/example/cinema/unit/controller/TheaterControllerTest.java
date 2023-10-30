package com.example.cinema.unit.controller;

import com.example.cinema.arguments_provider.IdAndNameArgumentsProvider;
import com.example.cinema.constants.TheaterConstants;
import com.example.cinema.controller.TheaterController;
import com.example.cinema.dto.TheaterDto;
import com.example.cinema.model.Theater;
import com.example.cinema.service.TheaterService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TheaterControllerTest {

    @Mock
    private TheaterService mockTheaterService;

    @InjectMocks
    private TheaterController theaterController;

    @ParameterizedTest
    @ArgumentsSource(IdAndNameArgumentsProvider.class)
    void Should_ListAllTheaters(Long id, String name) {
        Theater theater = TheaterConstants.getSimpleTheater(id, name);
        TheaterDto theaterDto = TheaterConstants.getSimpleTheaterDto(id, name);

        List<Theater> theaters = List.of(theater);
        List<TheaterDto> expected = List.of(theaterDto);

        when(mockTheaterService.findAll()).thenReturn(theaters);

        List<TheaterDto> actual = theaterController.findAll();

        assertAll(
                () -> assertNotNull(actual),
                () -> assertEquals(expected.size(), actual.size()),
                () -> assertEquals(expected.get(0).getId(), actual.get(0).getId()),
                () -> assertEquals(expected.get(0).getName(), actual.get(0).getName())
        );
    }

    @ParameterizedTest
    @ArgumentsSource(IdAndNameArgumentsProvider.class)
    void Should_ReturnTheaterDtoWithGivenId(Long id, String name) {
        Theater theater = TheaterConstants.getSimpleTheater(id, name);
        TheaterDto expected = TheaterConstants.getSimpleTheaterDto(id, name);

        when(mockTheaterService.findById(any())).thenReturn(theater);

        TheaterDto actual = theaterController.findById(id);

        assertAll(
                () -> assertNotNull(actual),
                () -> assertEquals(expected.getId(), actual.getId()),
                () -> assertEquals(expected.getName(), actual.getName())
        );
    }

}
