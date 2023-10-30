package com.example.cinema.integration;

import com.example.cinema.CinemaApplication;
import com.example.cinema.arguments_provider.NonExistingIdsArgumentsProvider;
import com.example.cinema.constants.ReservationConstants;
import com.example.cinema.dto.ReservationCreationByUserDto;
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
public class ReservationControllerTest {

    private static final String URL_PREFIX = "/reservation";

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype());

    @Autowired
    private MockMvc mvc;

    @Test
    @WithMockUser(username = "user@gmail.com", password = "$2a$10$E5bwurPH0uml1WmBsk/ooeIo/2aV2U4VOj93GOMgFQx5WQ.JcGNGS", authorities = "USER")
    @Transactional
    public void Should_SaveReservation() throws Exception {
        ReservationCreationByUserDto reservationCreationByUserDto = new ReservationCreationByUserDto(
                ReservationConstants.PROJECTION_ID,
                ReservationConstants.NUMBER_OF_TICKETS_TO_RESERVE
        );

        String json = TestUtil.json(reservationCreationByUserDto);
        mvc.perform(post(URL_PREFIX + "/reserve").contentType(contentType).content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[*].projectionDto.id").value(hasItem(ReservationConstants.PROJECTION_ID.intValue())))
                .andExpect(jsonPath("$", hasSize(ReservationConstants.NUMBER_OF_TICKETS_TO_RESERVE)));
    }

    @Test
    @WithMockUser(username = "error", password = "$2a$10$E5bwurPH0uml1WmBsk/ooeIo/2aV2U4VOj93GOMgFQx5WQ.JcGNGS", authorities = "USER")
    @Transactional
    public void Should_ReturnNotFound_When_UserNotValid() throws Exception {
        ReservationCreationByUserDto reservationCreationByUserDto = new ReservationCreationByUserDto(
                ReservationConstants.PROJECTION_ID,
                ReservationConstants.NUMBER_OF_TICKETS_TO_RESERVE
        );

        String json = TestUtil.json(reservationCreationByUserDto);
        mvc.perform(post(URL_PREFIX + "/reserve").contentType(contentType).content(json))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "ana@gmail.com", password = "$2a$10$E5bwurPH0uml1WmBsk/ooeIo/2aV2U4VOj93GOMgFQx5WQ.JcGNGS", authorities = "USER")
    @Transactional
    public void Should_ReturnBadRequest_When_UserReachedMaximumNumberOfTicketsPerProjection() throws Exception {
        ReservationCreationByUserDto reservationCreationByUserDto = new ReservationCreationByUserDto(
                ReservationConstants.PROJECTION_ID,
                ReservationConstants.NUMBER_OF_TICKETS_TO_RESERVE
        );

        String json = TestUtil.json(reservationCreationByUserDto);
        mvc.perform(post(URL_PREFIX + "/reserve").contentType(contentType).content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "user@gmail.com", password = "$2a$10$E5bwurPH0uml1WmBsk/ooeIo/2aV2U4VOj93GOMgFQx5WQ.JcGNGS", authorities = "USER")
    @Transactional
    public void Should_ReturnBadRequest_When_NotEnoughTicketsAvailable() throws Exception {
        ReservationCreationByUserDto reservationCreationByUserDto = new ReservationCreationByUserDto(
                ReservationConstants.PROJECTION_ID,
                ReservationConstants.MAX_NUMBER_OF_TICKETS_TO_RESERVE
        );

        String json = TestUtil.json(reservationCreationByUserDto);
        mvc.perform(post(URL_PREFIX + "/reserve").contentType(contentType).content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "user@gmail.com", password = "$2a$10$E5bwurPH0uml1WmBsk/ooeIo/2aV2U4VOj93GOMgFQx5WQ.JcGNGS", authorities = "USER")
    @Transactional
    public void Should_CancelReservationWithGivenId() throws Exception {
        mvc.perform(put(URL_PREFIX + "/" + ReservationConstants.DB_ID + "/cancel"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(ReservationConstants.DB_ID.intValue()));
    }

    @ParameterizedTest
    @ArgumentsSource(NonExistingIdsArgumentsProvider.class)
    @WithMockUser(username = "user@gmail.com", password = "$2a$10$E5bwurPH0uml1WmBsk/ooeIo/2aV2U4VOj93GOMgFQx5WQ.JcGNGS", authorities = "USER")
    @Transactional
    public void Should_ReturnNotFound_When_IdForDeletingDoesNotExist(Long id) throws Exception {
        mvc.perform(put(URL_PREFIX + "/" + id + "/cancel"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "admin@gmail.com", password = "$2a$10$E5bwurPH0uml1WmBsk/ooeIo/2aV2U4VOj93GOMgFQx5WQ.JcGNGS", authorities = "ADMIN")
    public void Should_ListAllActiveReservations() throws Exception {
        mvc.perform(get(URL_PREFIX))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(ReservationConstants.DB_COUNT_ACTIVE)))
                .andExpect(jsonPath("$.[*].id").value(hasItem(ReservationConstants.DB_ID.intValue())))
                .andExpect(jsonPath("$.[*].userDto.id").value(hasItem(ReservationConstants.DB_USER_ID.intValue())))
                .andExpect(jsonPath("$.[*].projectionDto.id").value(hasItem(ReservationConstants.DB_PROJECTION_ID.intValue())));
    }

    @Test
    @WithMockUser(username = "ana@gmail.com", password = "$2a$10$E5bwurPH0uml1WmBsk/ooeIo/2aV2U4VOj93GOMgFQx5WQ.JcGNGS", authorities = "USER")
    public void Should_ListAllActiveReservationsByUser() throws Exception {
        mvc.perform(get(URL_PREFIX + "/user"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(ReservationConstants.DB_COUNT_ACTIVE_BY_USER)))
                .andExpect(jsonPath("$.[*].userDto.id").value(hasItem(ReservationConstants.DB_USER_ID.intValue())))
                .andExpect(jsonPath("$.[*].projectionDto.id").value(hasItem(ReservationConstants.DB_PROJECTION_ID.intValue())));
    }

    @ParameterizedTest
    @ValueSource(longs = {1, 2, 3, 4, 5, 6})
    @WithMockUser(username = "user@gmail.com", password = "$2a$10$E5bwurPH0uml1WmBsk/ooeIo/2aV2U4VOj93GOMgFQx5WQ.JcGNGS", authorities = "USER")
    public void Should_ReturnReservationWithGivenId(Long id) throws Exception {
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
