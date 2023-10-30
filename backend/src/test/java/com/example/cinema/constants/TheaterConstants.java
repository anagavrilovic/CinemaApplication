package com.example.cinema.constants;

import com.example.cinema.dto.TheaterDto;
import com.example.cinema.model.Theater;

import java.util.ArrayList;

public class TheaterConstants {

    public static final Integer DB_COUNT = 3;
    public static final Long DB_ID = 1L;
    public static final String DB_NAME = "Grand Room";
    public static final Integer DB_NUMBER_OF_SEATS = 50;


    public static Theater getSimpleTheater(Long id, String name) {
        Theater theater = new Theater();
        theater.setId(id);
        theater.setName(name);
        theater.setProjections(new ArrayList<>());

        return theater;
    }

    public static TheaterDto getSimpleTheaterDto(Long id, String name) {
        TheaterDto theaterDto = new TheaterDto();
        theaterDto.setId(id);
        theaterDto.setName(name);

        return theaterDto;
    }
}
