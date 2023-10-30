package com.example.cinema.unit.service;

import com.example.cinema.arguments_provider.IdAndNameArgumentsProvider;
import com.example.cinema.arguments_provider.IdArgumentsProvider;
import com.example.cinema.constants.ProjectionConstants;
import com.example.cinema.dto.ProjectionCreationDto;
import com.example.cinema.exception.BusinessLogicException;
import com.example.cinema.exception.EntityNotFoundException;
import com.example.cinema.model.Projection;
import com.example.cinema.repository.ProjectionRepository;
import com.example.cinema.service.MovieService;
import com.example.cinema.service.TheaterService;
import com.example.cinema.service.impl.ProjectionServiceImpl;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProjectionServiceTest {

    @Mock
    private ProjectionRepository mockProjectionRepository;

    @Mock
    private MovieService mockMovieService;

    @Mock
    private TheaterService mockTheaterService;

    @InjectMocks
    private ProjectionServiceImpl projectionService;

    @ParameterizedTest
    @ArgumentsSource(IdArgumentsProvider.class)
    void Should_CreateNewProjection_When_StartDateAndTimeIsValid(Long id) {
        ProjectionCreationDto projectionCreationDto = new ProjectionCreationDto();
        projectionCreationDto.setStartDateAndTime(LocalDateTime.now().plusDays(2));
        projectionCreationDto.setTicketPrice(200);

        Projection expected = new Projection();
        expected.setId(id);
        expected.setStartDateAndTime(LocalDateTime.now().plusDays(2));
        expected.setTicketPrice(200);
        expected.setMovie(ProjectionConstants.getMovieForProjection(id));
        expected.setTheater(ProjectionConstants.getTheaterForProjection(id));

        when(mockMovieService.findById(any())).thenReturn(ProjectionConstants.getMovieForProjection(id));
        when(mockTheaterService.findById(any())).thenReturn(ProjectionConstants.getTheaterForProjection(id));
        when(mockProjectionRepository.findByTheaterIdAndDeletedFalse(any())).thenReturn(new ArrayList<>());
        when(mockProjectionRepository.save(any())).thenReturn(expected);

        Projection actual = projectionService.create(projectionCreationDto);

        assertAll(
                () -> assertNotNull(actual),
                () -> assertEquals(expected.getTicketPrice(), actual.getTicketPrice()),
                () -> assertEquals(expected.getMovie().getId(), actual.getMovie().getId()),
                () -> assertEquals(expected.getTheater().getId(), actual.getTheater().getId())
        );
    }

    @ParameterizedTest
    @ArgumentsSource(IdArgumentsProvider.class)
    void Should_ThrowBusinessLogicException_When_StartDateAndTimeAreInThePast(Long id) {
        ProjectionCreationDto projectionCreationDto = new ProjectionCreationDto();
        projectionCreationDto.setStartDateAndTime(LocalDateTime.now().minusDays(2));

        Executable executable = () -> projectionService.create(projectionCreationDto);

        assertThrows(BusinessLogicException.class, executable);
    }

    @ParameterizedTest
    @ArgumentsSource(IdArgumentsProvider.class)
    void Should_CreateNewProjection_When_ThereIsNoOverlappingProjectionInTheSameTheater(Long id) {
        ProjectionCreationDto projectionCreationDto = new ProjectionCreationDto();
        projectionCreationDto.setStartDateAndTime(LocalDateTime.now().plusDays(2));
        projectionCreationDto.setTicketPrice(200);

        Projection expected = new Projection();
        expected.setId(id);
        expected.setStartDateAndTime(projectionCreationDto.getStartDateAndTime());
        expected.setTicketPrice(200);
        expected.setMovie(ProjectionConstants.getMovieForProjection(id));
        expected.setTheater(ProjectionConstants.getTheaterForProjection(id));

        when(mockMovieService.findById(any())).thenReturn(ProjectionConstants.getMovieForProjection(id));
        when(mockTheaterService.findById(any())).thenReturn(ProjectionConstants.getTheaterForProjection(id));
        when(mockProjectionRepository.findByTheaterIdAndDeletedFalse(any()))
                .thenReturn(ProjectionConstants.getProjectionsThatDoNotOverlap
                        (projectionCreationDto.getStartDateAndTime(), expected.getMovie().getLength()));
        when(mockProjectionRepository.save(any())).thenReturn(expected);

        Projection actual = projectionService.create(projectionCreationDto);

        assertAll(
                () -> assertNotNull(actual),
                () -> assertEquals(expected.getTicketPrice(), actual.getTicketPrice()),
                () -> assertEquals(expected.getMovie().getId(), actual.getMovie().getId()),
                () -> assertEquals(expected.getTheater().getId(), actual.getTheater().getId())
        );
    }

    @ParameterizedTest
    @ArgumentsSource(IdArgumentsProvider.class)
    void Should_ThrowBusinessLogicException_When_ThereAreOverlappingProjectionInTheSameTheater(Long id) {
        ProjectionCreationDto projectionCreationDto = new ProjectionCreationDto();
        projectionCreationDto.setStartDateAndTime(LocalDateTime.now().plusDays(2));
        projectionCreationDto.setTicketPrice(200);

        when(mockMovieService.findById(any())).thenReturn(ProjectionConstants.getMovieForProjection(id));
        when(mockTheaterService.findById(any())).thenReturn(ProjectionConstants.getTheaterForProjection(id));
        when(mockProjectionRepository.findByTheaterIdAndDeletedFalse(any()))
                .thenReturn(ProjectionConstants.getProjectionsThatDoOverlap
                        (projectionCreationDto.getStartDateAndTime(), 150));

        Executable executable = () -> projectionService.create(projectionCreationDto);

        assertThrows(BusinessLogicException.class, executable);
    }

    @ParameterizedTest
    @ArgumentsSource(IdArgumentsProvider.class)
    void Should_ReturnDeletedProjectionId_WhenProjectionForDeletingExists(Long id) {
        Projection expected = new Projection();
        expected.setId(id);

        when(mockProjectionRepository.findByIdAndDeletedFalse(any())).thenReturn(Optional.of(expected));

        Long actualId = projectionService.delete(id);

        assertEquals(actualId, expected.getId());
    }

    @ParameterizedTest
    @ArgumentsSource(IdArgumentsProvider.class)
    void Should_ThrowEntityNotFoundException_WhenMovieForDeletingNotExists(Long id) {
        when(mockProjectionRepository.findByIdAndDeletedFalse(any())).thenReturn(Optional.empty());

        Executable executable = () -> projectionService.delete(id);

        assertThrows(EntityNotFoundException.class, executable);
    }

    @ParameterizedTest
    @ArgumentsSource(IdAndNameArgumentsProvider.class)
    void Should_ReturnAvailableProjectionList(Long id) {
        Projection projection = new Projection();
        projection.setId(id);
        projection.setDeleted(false);

        List<Projection> projectionsExpected = List.of(projection);

        when(mockProjectionRepository.findAllAvailable()).thenReturn(projectionsExpected);

        List<Projection> actual = projectionService.findAllAvailable();

        assertAll(
                () -> assertNotNull(actual),
                () -> assertEquals(actual.size(), projectionsExpected.size()),
                () -> assertEquals(actual.get(0).getId(), projectionsExpected.get(0).getId()),
                () -> assertFalse(actual.get(0).getDeleted())
        );
    }

    @ParameterizedTest
    @ArgumentsSource(IdAndNameArgumentsProvider.class)
    void Should_ReturnProjectionList(Long id) {
        Projection projection = new Projection();
        projection.setId(id);
        projection.setDeleted(false);

        List<Projection> projectionsExpected = List.of(projection);

        when(mockProjectionRepository.findAll()).thenReturn(projectionsExpected);

        List<Projection> actual = projectionService.findAll();

        assertAll(
                () -> assertNotNull(actual),
                () -> assertEquals(actual.size(), projectionsExpected.size()),
                () -> assertEquals(actual.get(0).getId(), projectionsExpected.get(0).getId()),
                () -> assertFalse(actual.get(0).getDeleted())
        );
    }

    @ParameterizedTest
    @ArgumentsSource(IdArgumentsProvider.class)
    void Should_ReturnProjectionWithGivenId_WhenProjectionExists(Long id) {
        Projection expected = new Projection();
        expected.setId(id);
        expected.setDeleted(false);

        when(mockProjectionRepository.findByIdAndDeletedFalse(any())).thenReturn(Optional.of(expected));

        Projection actual = projectionService.findById(id);

        assertAll(
                () -> assertNotNull(actual),
                () -> assertEquals(actual.getId(), expected.getId()),
                () -> assertFalse(actual.getDeleted())
        );
    }

    @ParameterizedTest
    @ArgumentsSource(IdArgumentsProvider.class)
    void Should_ThrowEntityNotFoundException_WhenProjectionNotExists(Long id) {
        when(mockProjectionRepository.findByIdAndDeletedFalse(any())).thenReturn(Optional.empty());

        Executable executable = () -> projectionService.findById(id);

        assertThrows(EntityNotFoundException.class, executable);
    }

}
