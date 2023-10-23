package com.example.cinema.unit.service;

import com.example.cinema.arguments_provider.IdArgumentsProvider;
import com.example.cinema.arguments_provider.IdAndNameArgumentsProvider;
import com.example.cinema.constants.MovieConstants;
import com.example.cinema.dto.MovieForUpdateDto;
import com.example.cinema.exception.BusinessLogicException;
import com.example.cinema.exception.EntityNotFoundException;
import com.example.cinema.exception.ObjectAlreadyExistsException;
import com.example.cinema.model.Movie;
import com.example.cinema.repository.MovieRepository;
import com.example.cinema.service.impl.MovieServiceImpl;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
    @ArgumentsSource(IdAndNameArgumentsProvider.class)
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
    @ArgumentsSource(IdAndNameArgumentsProvider.class)
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
    @ArgumentsSource(IdAndNameArgumentsProvider.class)
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
    @ArgumentsSource(IdArgumentsProvider.class)
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
    @ArgumentsSource(IdArgumentsProvider.class)
    void Should_ThrowEntityNotFoundException_WhenMovieNotExists(Long id) {
        when(mockMovieRepository.findByIdAndDeletedFalse(any())).thenReturn(Optional.empty());

        Executable executable = () -> movieService.findById(id);

        assertThrows(EntityNotFoundException.class, executable);
    }

    @ParameterizedTest
    @ArgumentsSource(IdArgumentsProvider.class)
    void Should_ReturnDeletedMovieId_WhenMovieForDeletingDoesNotHaveActiveProjection(Long id) {
        Movie expected = MovieConstants.getMovieWithAllPassedProjections(id);

        when(mockMovieRepository.findByIdAndDeletedFalse(any())).thenReturn(Optional.of(expected));

        Long actualId = movieService.delete(id);

        assertEquals(actualId, expected.getId());
    }

    @ParameterizedTest
    @ArgumentsSource(IdArgumentsProvider.class)
    void Should_ThrowBusinessLogicException_WhenMovieForDeletingHaveActiveProjection(Long id) {
        Movie expected = MovieConstants.getMovieWithActiveProjections(id);

        when(mockMovieRepository.findByIdAndDeletedFalse(any())).thenReturn(Optional.of(expected));

        Executable executable = () -> movieService.delete(id);

        assertThrows(BusinessLogicException.class, executable);
    }

    @ParameterizedTest
    @ArgumentsSource(IdArgumentsProvider.class)
    void Should_ThrowBusinessLogicException_WhenMovieForDeletingCurrentlyHaveActiveProjection(Long id) {
        Movie expected = MovieConstants.getMovieWithCurrentlyActiveProjection(id);

        when(mockMovieRepository.findByIdAndDeletedFalse(any())).thenReturn(Optional.of(expected));

        Executable executable = () -> movieService.delete(id);

        assertThrows(BusinessLogicException.class, executable);
    }

    @ParameterizedTest
    @ArgumentsSource(IdArgumentsProvider.class)
    void Should_ThrowEntityNotFoundException_WhenMovieForDeletingNotExists(Long id) {
        when(mockMovieRepository.findByIdAndDeletedFalse(any())).thenReturn(Optional.empty());

        Executable executable = () -> movieService.delete(id);

        assertThrows(EntityNotFoundException.class, executable);
    }

    @ParameterizedTest
    @ArgumentsSource(IdArgumentsProvider.class)
    void Should_ThrowEntityNotFoundException_WhenMovieForUpdatingNotExists(Long id) {
        MovieForUpdateDto movieForUpdateDto = new MovieForUpdateDto(id, null, null, null, null, null);

        when(mockMovieRepository.findByIdAndDeletedFalse(any())).thenReturn(Optional.empty());

        Executable executable = () -> movieService.update(movieForUpdateDto);

        assertThrows(EntityNotFoundException.class, executable);
    }

    @ParameterizedTest
    @ArgumentsSource(IdArgumentsProvider.class)
    void Should_ReturnDeletedMovieId_WhenMovieForUpdatingDoesNotHaveActiveProjection(Long id) {
        Movie expected = MovieConstants.getMovieWithAllPassedProjections(id);

        when(mockMovieRepository.findByIdAndDeletedFalse(any())).thenReturn(Optional.of(expected));
        when(mockMovieRepository.save(any())).thenReturn(expected);

        Movie actual = movieService.update(new MovieForUpdateDto());

        assertAll(
                () -> assertNotNull(actual),
                () -> assertEquals(expected.getId(), actual.getId())
        );
    }

    @ParameterizedTest
    @ArgumentsSource(IdArgumentsProvider.class)
    void Should_ThrowBusinessLogicException_WhenMovieForUpdatingHaveActiveProjection(Long id) {
        Movie expected = MovieConstants.getMovieWithActiveProjections(id);

        when(mockMovieRepository.findByIdAndDeletedFalse(any())).thenReturn(Optional.of(expected));

        Executable executable = () -> movieService.update(new MovieForUpdateDto());

        assertThrows(BusinessLogicException.class, executable);
    }

    @ParameterizedTest
    @ArgumentsSource(IdArgumentsProvider.class)
    void Should_ThrowBusinessLogicException_WhenMovieForUpdatingCurrentlyHaveActiveProjection(Long id) {
        Movie expected = MovieConstants.getMovieWithCurrentlyActiveProjection(id);

        when(mockMovieRepository.findByIdAndDeletedFalse(any())).thenReturn(Optional.of(expected));

        Executable executable = () -> movieService.update(new MovieForUpdateDto());

        assertThrows(BusinessLogicException.class, executable);
    }

}
