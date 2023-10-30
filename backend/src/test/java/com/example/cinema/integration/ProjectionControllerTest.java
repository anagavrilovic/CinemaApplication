package com.example.cinema.integration;

import com.example.cinema.CinemaApplication;
import com.example.cinema.arguments_provider.NonExistingIdsArgumentsProvider;
import com.example.cinema.arguments_provider.ProjectionCreationDtoArgumentsProvider;
import com.example.cinema.constants.MovieConstants;
import com.example.cinema.constants.ProjectionConstants;
import com.example.cinema.dto.ProjectionCreationDto;
import com.example.cinema.util.TestUtil;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = CinemaApplication.class)
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ProjectionControllerTest {

    private static final String URL_PREFIX = "/projection";

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype());

    @Autowired
    private MockMvc mvc;

    @ParameterizedTest
    @ArgumentsSource(ProjectionCreationDtoArgumentsProvider.class)
    @WithMockUser(username = "admin@gmail.com", password = "$2a$10$E5bwurPH0uml1WmBsk/ooeIo/2aV2U4VOj93GOMgFQx5WQ.JcGNGS", authorities = "ADMIN")
    @Transactional
    public void Should_SaveProjection(ProjectionCreationDto projectionCreationDto) throws Exception {
        String json = TestUtil.json(projectionCreationDto);
        mvc.perform(post(URL_PREFIX).contentType(contentType).content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.movieDto.id").value(ProjectionConstants.MOVIE_ID))
                .andExpect(jsonPath("$.theaterDto.id").value(ProjectionConstants.THEATER_ID))
                .andExpect(jsonPath("$.ticketPrice").value(ProjectionConstants.TICKET_PRICE));
    }

    @Test
    @WithMockUser(username = "admin@gmail.com", password = "$2a$10$E5bwurPH0uml1WmBsk/ooeIo/2aV2U4VOj93GOMgFQx5WQ.JcGNGS", authorities = "ADMIN")
    @Transactional
    public void Should_ReturnBadRequest_When_TryingToSaveProjectionInThePast() throws Exception {
        ProjectionCreationDto projectionCreationDto = new ProjectionCreationDto();
        projectionCreationDto.setMovieId(ProjectionConstants.MOVIE_ID);
        projectionCreationDto.setTheaterId(ProjectionConstants.THEATER_ID);
        projectionCreationDto.setTicketPrice(ProjectionConstants.TICKET_PRICE);
        projectionCreationDto.setStartDateAndTime(ProjectionConstants.ONE_DAY_BEFORE_NOW);

        String json = TestUtil.json(projectionCreationDto);
        mvc.perform(post(URL_PREFIX).contentType(contentType).content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "admin@gmail.com", password = "$2a$10$E5bwurPH0uml1WmBsk/ooeIo/2aV2U4VOj93GOMgFQx5WQ.JcGNGS", authorities = "ADMIN")
    @Transactional
    public void Should_ReturnBadRequest_When_ProjectionsOverlap() throws Exception {
        ProjectionCreationDto projectionCreationDto = new ProjectionCreationDto();
        projectionCreationDto.setMovieId(ProjectionConstants.DB_MOVIE_ID);
        projectionCreationDto.setTheaterId(ProjectionConstants.DB_THEATER_ID);
        projectionCreationDto.setTicketPrice(ProjectionConstants.TICKET_PRICE);
        projectionCreationDto.setStartDateAndTime(ProjectionConstants.ONE_DAY_AFTER_NOW);

        String json = TestUtil.json(projectionCreationDto);
        mvc.perform(post(URL_PREFIX).contentType(contentType).content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "admin@gmail.com", password = "$2a$10$E5bwurPH0uml1WmBsk/ooeIo/2aV2U4VOj93GOMgFQx5WQ.JcGNGS", authorities = "ADMIN")
    @Transactional
    public void Should_DeleteProjectionWithGivenId() throws Exception {
        mvc.perform(delete(URL_PREFIX + "/" + ProjectionConstants.DB_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(MovieConstants.DB_ID.intValue()));
    }

    @ParameterizedTest
    @ArgumentsSource(NonExistingIdsArgumentsProvider.class)
    @WithMockUser(username = "admin@gmail.com", password = "$2a$10$E5bwurPH0uml1WmBsk/ooeIo/2aV2U4VOj93GOMgFQx5WQ.JcGNGS", authorities = "ADMIN")
    @Transactional
    public void Should_ReturnNotFound_When_IdForDeletingDoesNotExist(Long id) throws Exception {
        mvc.perform(delete(URL_PREFIX + "/" + id))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "user@gmail.com", password = "$2a$10$E5bwurPH0uml1WmBsk/ooeIo/2aV2U4VOj93GOMgFQx5WQ.JcGNGS", authorities = "USER")
    public void Should_ListAllAvailableProjections() throws Exception {
        mvc.perform(get(URL_PREFIX + "/available"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(ProjectionConstants.DB_COUNT)))
                .andExpect(jsonPath("$.[*].id").value(hasItem(ProjectionConstants.DB_ID.intValue())))
                .andExpect(jsonPath("$.[*].movieDto.id").value(hasItem(ProjectionConstants.DB_MOVIE_ID.intValue())))
                .andExpect(jsonPath("$.[*].theaterDto.id").value(hasItem(ProjectionConstants.DB_THEATER_ID.intValue())))
                .andExpect(jsonPath("$.[*].ticketPrice").value(hasItem(ProjectionConstants.DB_TICKET_PRICE)));
    }

    @Test
    @WithMockUser(username = "user@gmail.com", password = "$2a$10$E5bwurPH0uml1WmBsk/ooeIo/2aV2U4VOj93GOMgFQx5WQ.JcGNGS", authorities = "USER")
    public void Should_ListAllProjections() throws Exception {
        mvc.perform(get(URL_PREFIX))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(ProjectionConstants.DB_COUNT)))
                .andExpect(jsonPath("$.[*].id").value(hasItem(ProjectionConstants.DB_ID.intValue())))
                .andExpect(jsonPath("$.[*].movieDto.id").value(hasItem(ProjectionConstants.DB_MOVIE_ID.intValue())))
                .andExpect(jsonPath("$.[*].theaterDto.id").value(hasItem(ProjectionConstants.DB_THEATER_ID.intValue())))
                .andExpect(jsonPath("$.[*].ticketPrice").value(hasItem(ProjectionConstants.DB_TICKET_PRICE)));
    }


    @ParameterizedTest
    @ValueSource(longs = {1, 2, 3})
    @WithMockUser(username = "user@gmail.com", password = "$2a$10$E5bwurPH0uml1WmBsk/ooeIo/2aV2U4VOj93GOMgFQx5WQ.JcGNGS", authorities = "USER")
    public void Should_ReturnProjectionWithGivenId(Long id) throws Exception {
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
