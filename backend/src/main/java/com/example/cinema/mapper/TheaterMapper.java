package com.example.cinema.mapper;

import com.example.cinema.dto.TheaterDto;
import com.example.cinema.model.Theater;

import java.util.ArrayList;
import java.util.List;

public final class TheaterMapper {

    public static List<TheaterDto> theatersToTheaterDtos(List<Theater> theaters) {
        List<TheaterDto> theaterDtos = new ArrayList<>();

        for (Theater theater : theaters) {
            theaterDtos.add(new TheaterDto(theater));
        }

        return theaterDtos;
    }

}
