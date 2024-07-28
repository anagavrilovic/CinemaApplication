package com.example.cinema.arguments_provider;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

public class UsernameAndPasswordArgumentsProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        return Stream.of(
                Arguments.of("ana@gmail.com", "123"),
                Arguments.of("sanja@gmail.com", "123"),
                Arguments.of("admin@gmail.com", "123"),
                Arguments.of("srdjan@gmail.com", "123"),
                Arguments.of("marija@gmail.com", "123")
        );
    }
}
