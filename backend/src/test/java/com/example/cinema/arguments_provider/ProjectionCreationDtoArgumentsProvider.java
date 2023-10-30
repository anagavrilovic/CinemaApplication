package com.example.cinema.arguments_provider;

import com.example.cinema.constants.ProjectionConstants;
import com.example.cinema.constants.SharedConstants;
import com.example.cinema.dto.ProjectionCreationDto;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.time.LocalDateTime;
import java.util.stream.Stream;

public class ProjectionCreationDtoArgumentsProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        return Stream.iterate(1, i -> i + 1)
                .limit(SharedConstants.numberOfIntegrationTestIterations)
                .map(i -> Arguments.of(new ProjectionCreationDto(
                        ProjectionConstants.MOVIE_ID,
                        ProjectionConstants.THEATER_ID,
                        LocalDateTime.now().plusDays(i),
                        ProjectionConstants.TICKET_PRICE
                )));
    }
}
