package com.example.cinema.arguments_provider;

import com.example.cinema.constants.MovieConstants;
import com.example.cinema.constants.SharedConstants;
import com.example.cinema.dto.MovieCreationDto;
import com.example.cinema.model.enums.Genre;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class MovieCreationDtoArgumentsProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        return Stream.iterate(1, i -> i + 1)
                .limit(SharedConstants.numberOfIntegrationTestIterations)
                .map(i -> Arguments.of(new MovieCreationDto(
                        "Name" + i,
                        "Director" + i,
                        List.of(Genre.COMEDY),
                        MovieConstants.MOVIE_LENGTH,
                        MovieConstants.DB_DESCRIPTION)));
    }
}
