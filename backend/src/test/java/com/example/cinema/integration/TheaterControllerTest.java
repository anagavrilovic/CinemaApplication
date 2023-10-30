package com.example.cinema.integration;

import com.example.cinema.CinemaApplication;
import com.example.cinema.arguments_provider.NonExistingIdsArgumentsProvider;
import com.example.cinema.constants.MovieConstants;
import com.example.cinema.constants.TheaterConstants;
import jakarta.transaction.Transactional;
import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = CinemaApplication.class)
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class TheaterControllerTest {


    private static final String URL_PREFIX = "/theater";

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype());

    @Autowired
    private MockMvc mvc;

    @Test
    @WithMockUser(username = "user@gmail.com", password = "$2a$10$E5bwurPH0uml1WmBsk/ooeIo/2aV2U4VOj93GOMgFQx5WQ.JcGNGS", authorities = "USER")
    public void Should_ListAllTheaters() throws Exception {
        mvc.perform(get(URL_PREFIX))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(TheaterConstants.DB_COUNT)))
                .andExpect(jsonPath("$.[*].id").value(hasItem(TheaterConstants.DB_ID.intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(TheaterConstants.DB_NAME)))
                .andExpect(jsonPath("$.[*].numberOfSeats").value(hasItem(TheaterConstants.DB_NUMBER_OF_SEATS)));
    }

    @ParameterizedTest
    @ValueSource(longs = {1, 2, 3})
    @WithMockUser(username = "user@gmail.com", password = "$2a$10$E5bwurPH0uml1WmBsk/ooeIo/2aV2U4VOj93GOMgFQx5WQ.JcGNGS", authorities = "USER")
    public void Should_ReturnTheaterWithGivenId(Long id) throws Exception {
        mvc.perform(get(URL_PREFIX + "/" + id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.id").value(id.intValue()));
    }

    @ParameterizedTest
    @ArgumentsSource(NonExistingIdsArgumentsProvider.class)
    @WithMockUser(username = "admin@gmail.com", password = "$2a$10$E5bwurPH0uml1WmBsk/ooeIo/2aV2U4VOj93GOMgFQx5WQ.JcGNGS", authorities = "ADMIN")
    @Transactional
    public void Should_ReturnNotFound_When_IdDoesNotExist(Long id) throws Exception {
        mvc.perform(get(URL_PREFIX + "/" + id))
                .andExpect(status().isNotFound());
    }

}
