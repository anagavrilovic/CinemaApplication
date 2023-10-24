package com.example.cinema.arguments_provider;

import com.example.cinema.constants.SharedConstants;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

public class NameArgumentsProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        return Stream.iterate(1, i -> i + 1)
                .limit(SharedConstants.numberOfTestIterations)
                .map(i -> Arguments.of("Movie Name " + i));
    }
}
