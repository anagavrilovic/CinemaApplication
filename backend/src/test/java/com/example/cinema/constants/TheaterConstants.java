package com.example.cinema.constants;

import com.example.cinema.dto.TheaterDto;
import com.example.cinema.model.Theater;

import java.util.ArrayList;

public class TheaterConstants {

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
