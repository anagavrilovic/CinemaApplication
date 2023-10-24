package com.example.cinema.unit.service;

import com.example.cinema.arguments_provider.IdArgumentsProvider;
import com.example.cinema.constants.ReservationConstants;
import com.example.cinema.dto.ReservationCreationDto;
import com.example.cinema.exception.BusinessLogicException;
import com.example.cinema.exception.EntityNotFoundException;
import com.example.cinema.model.Reservation;
import com.example.cinema.model.User;
import com.example.cinema.repository.ReservationRepository;
import com.example.cinema.service.ProjectionService;
import com.example.cinema.service.UserService;
import com.example.cinema.service.impl.ReservationServiceImpl;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceTest {

    @Mock
    ReservationRepository mockReservationRepository;

    @Mock
    ProjectionService mockProjectionService;

    @Mock
    UserService mockUserService;

    @InjectMocks
    ReservationServiceImpl reservationService;

    @ParameterizedTest
    @ArgumentsSource(IdArgumentsProvider.class)
    void Should_CreateNewMovie_When_ReservationIsValid(Long id) {
        ReservationCreationDto reservationCreationDto = new ReservationCreationDto();
        reservationCreationDto.setProjectionId(id);
        reservationCreationDto.setUserId(id);
        reservationCreationDto.setNumberOfTickets(ReservationConstants.NUMBER_OF_TICKETS_TO_RESERVE);

        List<Reservation> reservationsExpected = ReservationConstants.getExpectedReservations
                (id, ReservationConstants.NUMBER_OF_TICKETS_TO_RESERVE);

        when(mockReservationRepository.countByUserIdAndProjectionId(any(), any()))
                .thenReturn(ReservationConstants.ALREADY_RESERVED_TICKETS_BY_USER_AVAILABLE_MORE);
        when(mockProjectionService.findById(any())).thenReturn(ReservationConstants.getAvailableProjectionForReservation(id));
        when(mockUserService.findById(any())).thenReturn(ReservationConstants.getUserForReservation(id));
        when(mockReservationRepository.save(any())).thenReturn(ReservationConstants.getReservation(id));

        List<Reservation> actual = reservationService.create(reservationCreationDto);

        assertAll(
                () -> assertNotNull(actual),
                () -> assertEquals(reservationsExpected.size(), actual.size()),
                () -> assertEquals(reservationsExpected.get(0).getId(), actual.get(0).getId())
        );
    }

    @ParameterizedTest
    @ArgumentsSource(IdArgumentsProvider.class)
    void Should_ThrowBusinessLogicException_When_UserReachedMaximumNumberOfReservations(Long id) {
        ReservationCreationDto reservationCreationDto = new ReservationCreationDto();
        reservationCreationDto.setProjectionId(id);
        reservationCreationDto.setUserId(id);
        reservationCreationDto.setNumberOfTickets(ReservationConstants.NUMBER_OF_TICKETS_TO_RESERVE);

        when(mockReservationRepository.countByUserIdAndProjectionId(any(), any()))
                .thenReturn(ReservationConstants.ALREADY_RESERVED_TICKETS_BY_USER_NOT_AVAILABLE_MORE);

        Executable executable = () -> reservationService.create(reservationCreationDto);

        assertThrows(BusinessLogicException.class, executable);
    }

    @ParameterizedTest
    @ArgumentsSource(IdArgumentsProvider.class)
    void Should_ThrowBusinessLogicException_When_NotEnoughAvailableSeats(Long id) {
        ReservationCreationDto reservationCreationDto = new ReservationCreationDto();
        reservationCreationDto.setProjectionId(id);
        reservationCreationDto.setUserId(id);
        reservationCreationDto.setNumberOfTickets(2);

        when(mockReservationRepository.countByUserIdAndProjectionId(any(), any()))
                .thenReturn(ReservationConstants.ALREADY_RESERVED_TICKETS_BY_USER_AVAILABLE_MORE);
        when(mockProjectionService.findById(any())).thenReturn(ReservationConstants.getUnavailableProjectionForReservation(id));

        Executable executable = () -> reservationService.create(reservationCreationDto);

        assertThrows(BusinessLogicException.class, executable);
    }

    @ParameterizedTest
    @ArgumentsSource(IdArgumentsProvider.class)
    void Should_CancelReservation_When_ProjectionIsNotIn2HoursOrLess(Long id) {
        Reservation reservation = ReservationConstants.getReservation(id);
        reservation.getProjection().setStartDateAndTime(ReservationConstants.FIVE_DAYS_FROM_NOW);
        reservation.getProjection().setNumberOfAvailableSeats(0);

        Long expected = id;

        when(mockReservationRepository.findById(any())).thenReturn(Optional.of(reservation));

        Long actual = reservationService.cancel(id);

        assertAll(
                () -> assertNotNull(actual),
                () -> assertEquals(expected, actual)
        );
    }

    @ParameterizedTest
    @ArgumentsSource(IdArgumentsProvider.class)
    void Should_ThrowEntityNotFoundException_When_ReservationForCancelingDoesNotExist(Long id) {
        when(mockReservationRepository.findById(any())).thenReturn(Optional.empty());

        Executable executable = () -> reservationService.cancel(id);

        assertThrows(EntityNotFoundException.class, executable);
    }

    @ParameterizedTest
    @ArgumentsSource(IdArgumentsProvider.class)
    void Should_ThrowBusinessLogicException_When_ReservationForCancelingIsIn2HoursOrLess(Long id) {
        Reservation reservation = ReservationConstants.getReservation(id);
        reservation.getProjection().setStartDateAndTime(ReservationConstants.FIVE_MINUTES_FROM_NOW);

        when(mockReservationRepository.findById(any())).thenReturn(Optional.of(reservation));

        Executable executable = () -> reservationService.cancel(id);

        assertThrows(BusinessLogicException.class, executable);
    }

    @ParameterizedTest
    @ArgumentsSource(IdArgumentsProvider.class)
    void Should_ReturnActiveReservationList(Long id) {
        Reservation reservation = new Reservation();
        reservation.setId(id);
        reservation.setCanceled(false);

        List<Reservation> reservationsExpected = List.of(reservation);

        when(mockReservationRepository.findAllByCanceledFalse()).thenReturn(reservationsExpected);

        List<Reservation> actual = reservationService.findAllActive();

        assertAll(
                () -> assertNotNull(actual),
                () -> assertEquals(actual.size(), reservationsExpected.size()),
                () -> assertEquals(actual.get(0).getId(), reservationsExpected.get(0).getId()),
                () -> assertFalse(actual.get(0).isCanceled())
        );
    }

    @ParameterizedTest
    @ArgumentsSource(IdArgumentsProvider.class)
    void Should_ReturnActiveReservationListForUser(Long id) {
        Reservation reservation = new Reservation();
        reservation.setId(id);
        reservation.setCanceled(false);

        List<Reservation> reservationsExpected = List.of(reservation);

        when(mockUserService.findByEmail(any())).thenReturn(new User());
        when(mockReservationRepository.findAllByCanceledFalseAndUserId(any())).thenReturn(reservationsExpected);

        List<Reservation> actual = reservationService.findAllActiveByUser(id.toString());

        assertAll(
                () -> assertNotNull(actual),
                () -> assertEquals(actual.size(), reservationsExpected.size()),
                () -> assertEquals(actual.get(0).getId(), reservationsExpected.get(0).getId()),
                () -> assertFalse(actual.get(0).isCanceled())
        );
    }

    @ParameterizedTest
    @ArgumentsSource(IdArgumentsProvider.class)
    void Should_ReturnReservationWithGivenId_WhenReservationExists(Long id) {
        Reservation expected = new Reservation();
        expected.setId(id);
        expected.setCanceled(false);

        when(mockReservationRepository.findById(any())).thenReturn(Optional.of(expected));

        Reservation actual = reservationService.findById(id);

        assertAll(
                () -> assertNotNull(actual),
                () -> assertEquals(actual.getId(), expected.getId()),
                () -> assertFalse(actual.isCanceled())
        );
    }

    @ParameterizedTest
    @ArgumentsSource(IdArgumentsProvider.class)
    void Should_ThrowEntityNotFoundException_WhenMovieNotExists(Long id) {
        when(mockReservationRepository.findById(any())).thenReturn(Optional.empty());

        Executable executable = () -> reservationService.findById(id);

        assertThrows(EntityNotFoundException.class, executable);
    }

}
