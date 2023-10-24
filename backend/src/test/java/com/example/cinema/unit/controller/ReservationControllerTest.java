package com.example.cinema.unit.controller;

import com.example.cinema.arguments_provider.IdAndNameArgumentsProvider;
import com.example.cinema.arguments_provider.IdArgumentsProvider;
import com.example.cinema.constants.ReservationConstants;
import com.example.cinema.controller.ReservationController;
import com.example.cinema.dto.ReservationCreationByUserDto;
import com.example.cinema.dto.ReservationDto;
import com.example.cinema.model.Reservation;
import com.example.cinema.service.ReservationService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.security.Principal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReservationControllerTest {

    @Mock
    ReservationService mockReservationService;

    @InjectMocks
    ReservationController reservationController;

    @ParameterizedTest
    @ArgumentsSource(IdAndNameArgumentsProvider.class)
    void Should_CreateNewReservation(Long id, String name) {
        ReservationCreationByUserDto request = new ReservationCreationByUserDto();
        request.setProjectionId(id);

        Reservation reservation = ReservationConstants.getReservation(id);
        List<Reservation> reservations = List.of(reservation);

        ReservationDto reservationDto = ReservationConstants.getSimpleReservationDto(id);
        List<ReservationDto> expected = List.of(reservationDto);

        Principal mockPrincipal = Mockito.mock(Principal.class);

        when(mockReservationService.createByUser(any(), any())).thenReturn(reservations);

        List<ReservationDto> actual = reservationController.create(request, mockPrincipal);

        assertAll(
                () -> assertNotNull(actual),
                () -> assertEquals(expected.size(), actual.size()),
                () -> assertEquals(expected.get(0).getId(), actual.get(0).getId())
        );
    }

    @ParameterizedTest
    @ArgumentsSource(IdArgumentsProvider.class)
    void Should_CancelReservationWithGivenId(Long id) {
        Long expected = id;

        when(mockReservationService.cancel(any())).thenReturn(expected);

        Long actual = reservationController.cancel(id);

        assertAll(
                () -> assertNotNull(actual),
                () -> assertEquals(expected, actual)
        );
    }

    @ParameterizedTest
    @ArgumentsSource(IdAndNameArgumentsProvider.class)
    void Should_ListAllActiveReservations(Long id) {
        Reservation reservation = ReservationConstants.getReservation(id);
        ReservationDto reservationDto = ReservationConstants.getSimpleReservationDto(id);

        List<Reservation> reservations = List.of(reservation);
        List<ReservationDto> expected = List.of(reservationDto);

        when(mockReservationService.findAllActive()).thenReturn(reservations);

        List<ReservationDto> actual = reservationController.findAllActive();

        assertAll(
                () -> assertNotNull(actual),
                () -> assertEquals(expected.size(), actual.size()),
                () -> assertEquals(expected.get(0).getId(), actual.get(0).getId())
        );
    }

    @ParameterizedTest
    @ArgumentsSource(IdAndNameArgumentsProvider.class)
    void Should_ListAllActiveReservationsByUser(Long id) {
        Reservation reservation = ReservationConstants.getReservation(id);
        ReservationDto reservationDto = ReservationConstants.getSimpleReservationDto(id);

        List<Reservation> reservations = List.of(reservation);
        List<ReservationDto> expected = List.of(reservationDto);

        Principal mockPrincipal = Mockito.mock(Principal.class);

        when(mockReservationService.findAllActiveByUser(any())).thenReturn(reservations);

        List<ReservationDto> actual = reservationController.findAllActiveByUser(mockPrincipal);

        assertAll(
                () -> assertNotNull(actual),
                () -> assertEquals(expected.size(), actual.size()),
                () -> assertEquals(expected.get(0).getId(), actual.get(0).getId())
        );
    }

    @ParameterizedTest
    @ArgumentsSource(IdAndNameArgumentsProvider.class)
    void Should_ReturnReservationDtoWithGivenId(Long id) {
        Reservation reservation = ReservationConstants.getReservation(id);
        ReservationDto expected = ReservationConstants.getSimpleReservationDto(id);

        when(mockReservationService.findById(any())).thenReturn(reservation);

        ReservationDto actual = reservationController.findById(id);

        assertAll(
                () -> assertNotNull(actual),
                () -> assertEquals(expected.getId(), actual.getId())
        );
    }

}
