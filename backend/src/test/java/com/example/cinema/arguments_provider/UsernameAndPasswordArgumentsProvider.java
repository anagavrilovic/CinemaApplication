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
                Arguments.of("marija@gmail.com", "123"),
                Arguments.of("user1@gmail.com", "123"),
                Arguments.of("user2@gmail.com", "123"),
                Arguments.of("user3@gmail.com", "123"),
                Arguments.of("user4@gmail.com", "123"),
                Arguments.of("user5@gmail.com", "123"),
                Arguments.of("user6@gmail.com", "123"),
                Arguments.of("user7@gmail.com", "123"),
                Arguments.of("user8@gmail.com", "123"),
                Arguments.of("user9@gmail.com", "123"),
                Arguments.of("user10@gmail.com", "123"),
                Arguments.of("user11@gmail.com", "123"),
                Arguments.of("user12@gmail.com", "123"),
                Arguments.of("user13@gmail.com", "123"),
                Arguments.of("user14@gmail.com", "123"),
                Arguments.of("user15@gmail.com", "123"),
                Arguments.of("user16@gmail.com", "123"),
                Arguments.of("user17@gmail.com", "123"),
                Arguments.of("user18@gmail.com", "123"),
                Arguments.of("user19@gmail.com", "123"),
                Arguments.of("user20@gmail.com", "123"),
                Arguments.of("user21@gmail.com", "123"),
                Arguments.of("user22@gmail.com", "123"),
                Arguments.of("user23@gmail.com", "123"),
                Arguments.of("user24@gmail.com", "123")
        );
    }
}
