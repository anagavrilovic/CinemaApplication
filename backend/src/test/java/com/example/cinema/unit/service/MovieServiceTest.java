package com.example.cinema.unit.service;

import com.example.cinema.dto.MovieForUpdateDto;
import com.example.cinema.exception.BusinessLogicException;
import com.example.cinema.exception.EntityNotFoundException;
import com.example.cinema.exception.ObjectAlreadyExistsException;
import com.example.cinema.model.Movie;
import com.example.cinema.model.Projection;
import com.example.cinema.repository.MovieRepository;
import com.example.cinema.service.impl.MovieServiceImpl;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MovieServiceTest {

    @Mock
    MovieRepository mockMovieRepository;

    @InjectMocks
    MovieServiceImpl movieService;

    @ParameterizedTest
    @CsvSource({"1,Movie Name 1", "2,Movie Name 2", "3,Movie Name 3", "4,Movie Name 4", "5,Movie Name 5",
                "6,Movie Name 6", "7,Movie Name 7", "8,Movie Name 8", "9,Movie Name 9", "10,Movie Name 10"})
    void Should_CreateNewMovie_When_MovieNameNotAlreadyExists(Long movieId, String movieName) {
        Movie request = new Movie();
        request.setName(movieName);

        Movie expected = new Movie();
        expected.setId(movieId);
        expected.setName(movieName);

        when(mockMovieRepository.findByNameAndDeletedFalse(any())).thenReturn(Optional.empty());
        when(mockMovieRepository.save(any())).thenReturn(expected);

        Movie actual = movieService.save(request);

        assertAll(
                () -> assertNotNull(actual),
                () -> assertEquals(expected.getId(), actual.getId()),
                () -> assertEquals(expected.getName(), actual.getName())
        );
    }

    @ParameterizedTest
    @CsvSource({"1,Movie Name 1", "2,Movie Name 2", "3,Movie Name 3", "4,Movie Name 4", "5,Movie Name 5",
            "6,Movie Name 6", "7,Movie Name 7", "8,Movie Name 8", "9,Movie Name 9", "10,Movie Name 10"})
    void Should_ThrowObjectAlreadyExistsException_When_MovieNameAlreadyExists(Long movieId, String movieName) {
        Movie request = new Movie();
        request.setName(movieName);

        Movie expected = new Movie();
        expected.setId(movieId);
        expected.setName(movieName);

        when(mockMovieRepository.findByNameAndDeletedFalse(any())).thenReturn(Optional.of(expected));

        Executable executable = () -> movieService.save(request);

        assertThrows(ObjectAlreadyExistsException.class, executable);
    }

    @ParameterizedTest
    @CsvSource({"1,Movie Name 1", "2,Movie Name 2", "3,Movie Name 3", "4,Movie Name 4", "5,Movie Name 5",
            "6,Movie Name 6", "7,Movie Name 7", "8,Movie Name 8", "9,Movie Name 9", "10,Movie Name 10"})
    void Should_ReturnMovieList(Long movieId, String movieName) {
        Movie movie = new Movie();
        movie.setId(movieId);
        movie.setName(movieName);
        movie.setDeleted(false);

        List<Movie> moviesExpected = Collections.singletonList(movie);

        when(mockMovieRepository.findByDeletedFalse()).thenReturn(moviesExpected);

        List<Movie> actual = movieService.findAll();

        assertAll(
                () -> assertNotNull(actual),
                () -> assertEquals(actual.size(), moviesExpected.size()),
                () -> assertEquals(actual.get(0).getName(), moviesExpected.get(0).getName()),
                () -> assertEquals(actual.get(0).getId(), moviesExpected.get(0).getId()),
                () -> assertFalse(actual.get(0).getDeleted())
        );
    }

    @ParameterizedTest
    @ValueSource(longs = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10})
    void Should_ReturnMovieWithGivenId_WhenMovieExists(Long id) {
        Movie expected = new Movie();
        expected.setId(id);
        expected.setDeleted(false);

        when(mockMovieRepository.findByIdAndDeletedFalse(any())).thenReturn(Optional.of(expected));

        Movie actual = movieService.findById(id);

        assertAll(
                () -> assertNotNull(actual),
                () -> assertEquals(actual.getId(), expected.getId()),
                () -> assertFalse(actual.getDeleted())
        );
    }

    @ParameterizedTest
    @ValueSource(longs = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10})
    void Should_ThrowEntityNotFoundException_WhenMovieNotExists(Long id) {
        when(mockMovieRepository.findByIdAndDeletedFalse(any())).thenReturn(Optional.empty());

        Executable executable = () -> movieService.findById(id);

        assertThrows(EntityNotFoundException.class, executable);
    }

    @ParameterizedTest
    @ValueSource(longs = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10})
    void Should_ReturnDeletedMovieId_WhenMovieForDeletingDoesNotHaveActiveProjection(Long id) {
        Movie expected = getMovieWithAllPassedProjections(id);

        when(mockMovieRepository.findByIdAndDeletedFalse(any())).thenReturn(Optional.of(expected));

        Long actualId = movieService.delete(id);

        assertEquals(actualId, expected.getId());
    }

    @ParameterizedTest
    @ValueSource(longs = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10})
    void Should_ThrowBusinessLogicException_WhenMovieForDeletingHaveActiveProjection(Long id) {
        Movie expected = getMovieWithActiveProjections(id);

        when(mockMovieRepository.findByIdAndDeletedFalse(any())).thenReturn(Optional.of(expected));

        Executable executable = () -> movieService.delete(id);

        assertThrows(BusinessLogicException.class, executable);
    }

    @ParameterizedTest
    @ValueSource(longs = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10})
    void Should_ThrowBusinessLogicException_WhenMovieForDeletingCurrentlyHaveActiveProjection(Long id) {
        Movie expected = getMovieWithCurrentlyActiveProjection(id);

        when(mockMovieRepository.findByIdAndDeletedFalse(any())).thenReturn(Optional.of(expected));

        Executable executable = () -> movieService.delete(id);

        assertThrows(BusinessLogicException.class, executable);
    }

    @ParameterizedTest
    @ValueSource(longs = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10})
    void Should_ThrowEntityNotFoundException_WhenMovieForDeletingNotExists(Long id) {
        when(mockMovieRepository.findByIdAndDeletedFalse(any())).thenReturn(Optional.empty());

        Executable executable = () -> movieService.delete(id);

        assertThrows(EntityNotFoundException.class, executable);
    }

    @ParameterizedTest
    @ValueSource(longs = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10})
    void Should_ThrowEntityNotFoundException_WhenMovieForUpdatingNotExists(Long id) {
        MovieForUpdateDto movieForUpdateDto = new MovieForUpdateDto(id, null, null, null, null, null);

        when(mockMovieRepository.findByIdAndDeletedFalse(any())).thenReturn(Optional.empty());

        Executable executable = () -> movieService.update(movieForUpdateDto);

        assertThrows(EntityNotFoundException.class, executable);
    }

    @ParameterizedTest
    @ValueSource(longs = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10})
    void Should_ReturnDeletedMovieId_WhenMovieForUpdatingDoesNotHaveActiveProjection(Long id) {
        Movie expected = getMovieWithAllPassedProjections(id);

        when(mockMovieRepository.findByIdAndDeletedFalse(any())).thenReturn(Optional.of(expected));
        when(mockMovieRepository.save(any())).thenReturn(expected);

        Movie actual = movieService.update(new MovieForUpdateDto());

        assertAll(
                () -> assertNotNull(actual),
                () -> assertEquals(expected.getId(), actual.getId())
        );
    }

    @ParameterizedTest
    @ValueSource(longs = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10})
    void Should_ThrowBusinessLogicException_WhenMovieForUpdatingHaveActiveProjection(Long id) {
        Movie expected = getMovieWithActiveProjections(id);

        when(mockMovieRepository.findByIdAndDeletedFalse(any())).thenReturn(Optional.of(expected));

        Executable executable = () -> movieService.update(new MovieForUpdateDto());

        assertThrows(BusinessLogicException.class, executable);
    }

    @ParameterizedTest
    @ValueSource(longs = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10})
    void Should_ThrowBusinessLogicException_WhenMovieForUpdatingCurrentlyHaveActiveProjection(Long id) {
        Movie expected = getMovieWithCurrentlyActiveProjection(id);

        when(mockMovieRepository.findByIdAndDeletedFalse(any())).thenReturn(Optional.of(expected));

        Executable executable = () -> movieService.update(new MovieForUpdateDto());

        assertThrows(BusinessLogicException.class, executable);
    }

    private static Movie getMovieWithAllPassedProjections(Long id) {
        Movie expected = new Movie();
        expected.setId(id);
        expected.setDeleted(false);
        expected.setLength(150);

        Projection projection1 = new Projection();
        projection1.setStartDateAndTime(LocalDateTime.now().minusDays(3));
        projection1.setMovie(expected);

        Projection projection2 = new Projection();
        projection2.setStartDateAndTime(LocalDateTime.now().minusDays(2));
        projection2.setMovie(expected);

        expected.setProjections(List.of(projection1, projection2));
        return expected;
    }

    private static Movie getMovieWithActiveProjections(Long id) {
        Movie expected = new Movie();
        expected.setId(id);
        expected.setDeleted(false);
        expected.setLength(150);

        Projection projection1 = new Projection();
        projection1.setStartDateAndTime(LocalDateTime.now().minusDays(3));
        projection1.setMovie(expected);

        Projection projection2 = new Projection();
        projection2.setStartDateAndTime(LocalDateTime.now().minusDays(2));
        projection2.setMovie(expected);

        Projection projection3 = new Projection();
        projection3.setStartDateAndTime(LocalDateTime.now().plusDays(2));
        projection3.setMovie(expected);

        expected.setProjections(List.of(projection1, projection2, projection3));
        return expected;
    }

    private Movie getMovieWithCurrentlyActiveProjection(Long id) {
        Movie expected = new Movie();
        expected.setId(id);
        expected.setDeleted(false);
        expected.setLength(150);

        Projection projection1 = new Projection();
        projection1.setStartDateAndTime(LocalDateTime.now().minusMinutes(10));
        projection1.setMovie(expected);

        expected.setProjections(List.of(projection1));
        return expected;
    }
}
