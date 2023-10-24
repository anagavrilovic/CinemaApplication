package com.example.cinema.constants;

import com.example.cinema.dto.ProjectionDto;
import com.example.cinema.dto.ReservationDto;
import com.example.cinema.dto.UserDto;
import com.example.cinema.model.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ReservationConstants {
    
    public static final int NUMBER_OF_TICKETS_TO_RESERVE = 2;
    public static final int ALREADY_RESERVED_TICKETS_BY_USER_AVAILABLE_MORE = 0;
    public static final int ALREADY_RESERVED_TICKETS_BY_USER_NOT_AVAILABLE_MORE = 5;
    public static final int NUMBER_OF_AVAILABLE_SEATS_AVAILABLE_MORE = 40;
    public static final int NUMBER_OF_AVAILABLE_SEATS_NOT_AVAILABLE_MORE = 0;
    public static LocalDateTime FIVE_DAYS_FROM_NOW = LocalDateTime.now().plusDays(5);
    public static LocalDateTime FIVE_MINUTES_FROM_NOW = LocalDateTime.now().plusMinutes(5);

    public static List<Reservation> getExpectedReservations(Long id, int numberOfTickets) {
        List<Reservation> reservationsExpected = new ArrayList<>();

        for(int i = 0; i < numberOfTickets; i++) {
            reservationsExpected.add(getReservation(id));
        }

        return reservationsExpected;
    }

    public static Reservation getReservation(Long id) {
        Reservation reservation = new Reservation();
        reservation.setId(id);

        User user = new User();
        user.setId(id);

        Projection projection = getAvailableProjectionForReservation(id);

        reservation.setUser(user);
        reservation.setProjection(projection);
        return reservation;
    }

    public static User getUserForReservation(Long id) {
        User user = new User();
        user.setId(id);
        
        return user;
    }

    public static Projection getAvailableProjectionForReservation(Long id) {
        Projection projection = new Projection();
        projection.setId(id);
        projection.setNumberOfAvailableSeats(NUMBER_OF_AVAILABLE_SEATS_AVAILABLE_MORE);
        projection.setMovie(new Movie());
        projection.setTheater(new Theater());

        return projection;
    }

    public static Projection getUnavailableProjectionForReservation(Long id) {
        Projection projection = new Projection();
        projection.setId(id);
        projection.setNumberOfAvailableSeats(NUMBER_OF_AVAILABLE_SEATS_NOT_AVAILABLE_MORE);

        return projection;
    }

    public static ReservationDto getSimpleReservationDto(Long id) {
        ReservationDto reservationDto = new ReservationDto();
        reservationDto.setId(id);
        reservationDto.setProjectionDto(new ProjectionDto());
        reservationDto.setUserDto(new UserDto());

        return reservationDto;
    }
}
