package com.example.cinema.arguments_provider;

import com.example.cinema.constants.SharedConstants;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

public class NonExistingIdsArgumentsProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        return Stream.iterate(1000, i -> i + 1)
                .limit(SharedConstants.numberOfIntegrationTestIterations)
                .map(i -> Arguments.of(Long.valueOf(i)));
    }
}
