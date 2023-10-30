package com.example.cinema.integration;

import com.example.cinema.CinemaApplication;
import com.example.cinema.arguments_provider.MovieCreationDtoArgumentsProvider;
import com.example.cinema.arguments_provider.NonExistingIdsArgumentsProvider;
import com.example.cinema.constants.MovieConstants;
import com.example.cinema.dto.MovieCreationDto;
import com.example.cinema.model.Movie;
import com.example.cinema.model.enums.Genre;
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

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = CinemaApplication.class)
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class MovieControllerTest {

    private static final String URL_PREFIX = "/movie";

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype());

    @Autowired
    private MockMvc mvc;

    @ParameterizedTest
    @ArgumentsSource(MovieCreationDtoArgumentsProvider.class)
    @WithMockUser(username = "admin@gmail.com", password = "$2a$10$E5bwurPH0uml1WmBsk/ooeIo/2aV2U4VOj93GOMgFQx5WQ.JcGNGS", authorities = "ADMIN")
    @Transactional
    public void Should_SaveMovie(MovieCreationDto movieCreationDto) throws Exception {
        String json = TestUtil.json(movieCreationDto);
        mvc.perform(post(URL_PREFIX).contentType(contentType).content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length").value(MovieConstants.MOVIE_LENGTH))
                .andExpect(jsonPath("$.description").value(MovieConstants.DB_DESCRIPTION));
    }

    @Test
    @WithMockUser(username = "admin@gmail.com", password = "$2a$10$E5bwurPH0uml1WmBsk/ooeIo/2aV2U4VOj93GOMgFQx5WQ.JcGNGS", authorities = "ADMIN")
    @Transactional
    public void Should_ReturnBadRequest_When_ThereIsAlreadyMovieWithSameName() throws Exception {
        MovieCreationDto movieCreationDto = new MovieCreationDto();
        movieCreationDto.setName(MovieConstants.DB_NAME);
        movieCreationDto.setDescription(MovieConstants.DB_DESCRIPTION);
        movieCreationDto.setDirector(MovieConstants.DB_DIRECTOR);
        movieCreationDto.setGenres(List.of(Genre.ROMANCE));
        movieCreationDto.setLength(MovieConstants.DB_LENGTH);

        String json = TestUtil.json(movieCreationDto);
        mvc.perform(post(URL_PREFIX).contentType(contentType).content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "user@gmail.com", password = "$2a$10$E5bwurPH0uml1WmBsk/ooeIo/2aV2U4VOj93GOMgFQx5WQ.JcGNGS", authorities = "USER")
    public void Should_ListAllMovies() throws Exception {
        mvc.perform(get(URL_PREFIX))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(MovieConstants.DB_COUNT)))
                .andExpect(jsonPath("$.[*].id").value(hasItem(MovieConstants.DB_ID.intValue())))
                .andExpect(jsonPath("$.[*].director").value(hasItem(MovieConstants.DB_DIRECTOR)))
                .andExpect(jsonPath("$.[*].length").value(hasItem(MovieConstants.DB_LENGTH)))
                .andExpect(jsonPath("$.[*].name").value(hasItem(MovieConstants.DB_NAME)));
    }

    @ParameterizedTest
    @ValueSource(longs = {1, 2, 3, 4, 5, 6, 7})
    @WithMockUser(username = "user@gmail.com", password = "$2a$10$E5bwurPH0uml1WmBsk/ooeIo/2aV2U4VOj93GOMgFQx5WQ.JcGNGS", authorities = "USER")
    public void Should_ReturnMovieWithGivenId(Long id) throws Exception {
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

    @Test
    @WithMockUser(username = "admin@gmail.com", password = "$2a$10$E5bwurPH0uml1WmBsk/ooeIo/2aV2U4VOj93GOMgFQx5WQ.JcGNGS", authorities = "ADMIN")
    @Transactional
    public void Should_DeleteMovieWithGivenId() throws Exception {
        mvc.perform(delete(URL_PREFIX + "/" + MovieConstants.DB_ID))
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
    @WithMockUser(username = "admin@gmail.com", password = "$2a$10$E5bwurPH0uml1WmBsk/ooeIo/2aV2U4VOj93GOMgFQx5WQ.JcGNGS", authorities = "ADMIN")
    @Transactional
    public void Should_ReturnBadRequest_When_MovieForDeletingHasActiveProjection() throws Exception {
        mvc.perform(delete(URL_PREFIX + "/" + MovieConstants.DB_MOVIE_ID_WITH_ACTIVE_PROJECTION))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "admin@gmail.com", password = "$2a$10$E5bwurPH0uml1WmBsk/ooeIo/2aV2U4VOj93GOMgFQx5WQ.JcGNGS", authorities = "ADMIN")
    @Transactional
    public void Should_UpdateMovie() throws Exception {
        Movie movie = new Movie();
        movie.setId(MovieConstants.DB_ID);
        movie.setName(MovieConstants.DB_NAME);
        movie.setLength(MovieConstants.DB_LENGTH);
        movie.setDirector(MovieConstants.DB_DIRECTOR);
        movie.setGenres(Arrays.asList(Genre.ACTION));
        movie.setDescription(MovieConstants.DB_DESCRIPTION);

        String json = TestUtil.json(movie);
        mvc.perform(put(URL_PREFIX).contentType(contentType).content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(MovieConstants.DB_ID.intValue()))
                .andExpect(jsonPath("$.director").value(MovieConstants.DB_DIRECTOR))
                .andExpect(jsonPath("$.name").value(MovieConstants.DB_NAME))
                .andExpect(jsonPath("$.length").value(MovieConstants.DB_LENGTH))
                .andExpect(jsonPath("$.description").value(MovieConstants.DB_DESCRIPTION));
    }

    @Test
    @WithMockUser(username = "admin@gmail.com", password = "$2a$10$E5bwurPH0uml1WmBsk/ooeIo/2aV2U4VOj93GOMgFQx5WQ.JcGNGS", authorities = "ADMIN")
    @Transactional
    public void Should_ReturnBadRequest_When_MovieForUpdatingHasActiveProjection() throws Exception {
        Movie movie = new Movie();
        movie.setId(MovieConstants.DB_MOVIE_ID_WITH_ACTIVE_PROJECTION);
        movie.setName(MovieConstants.DB_NAME);
        movie.setLength(MovieConstants.DB_LENGTH);
        movie.setDirector(MovieConstants.DB_DIRECTOR);
        movie.setGenres(Arrays.asList(Genre.ACTION));
        movie.setDescription(MovieConstants.DB_DESCRIPTION);

        String json = TestUtil.json(movie);
        mvc.perform(put(URL_PREFIX).contentType(contentType).content(json))
                .andExpect(status().isBadRequest());
    }

    @ParameterizedTest
    @ArgumentsSource(NonExistingIdsArgumentsProvider.class)
    @WithMockUser(username = "admin@gmail.com", password = "$2a$10$E5bwurPH0uml1WmBsk/ooeIo/2aV2U4VOj93GOMgFQx5WQ.JcGNGS", authorities = "ADMIN")
    @Transactional
    public void Should_ReturnNotFound_When_IdForUpdatingDoesNotExist(Long id) throws Exception {
        Movie movie = new Movie();
        movie.setId(id);
        movie.setName(MovieConstants.DB_NAME);
        movie.setLength(MovieConstants.DB_LENGTH);
        movie.setDirector(MovieConstants.DB_DIRECTOR);
        movie.setGenres(Arrays.asList(Genre.ACTION));
        movie.setDescription(MovieConstants.DB_DESCRIPTION);

        String json = TestUtil.json(movie);
        mvc.perform(put(URL_PREFIX).contentType(contentType).content(json))
                .andExpect(status().isNotFound());
    }

}
