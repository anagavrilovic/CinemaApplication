package com.example.cinema.unit.controller;

import com.example.cinema.arguments_provider.IdAndNameArgumentsProvider;
import com.example.cinema.arguments_provider.IdArgumentsProvider;
import com.example.cinema.constants.ProjectionConstants;
import com.example.cinema.controller.ProjectionController;
import com.example.cinema.dto.ProjectionCreationDto;
import com.example.cinema.dto.ProjectionDto;
import com.example.cinema.model.Projection;
import com.example.cinema.service.ProjectionService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProjectionControllerTest {

    @Mock
    ProjectionService mockProjectionService;

    @InjectMocks
    ProjectionController projectionController;

    @ParameterizedTest
    @ArgumentsSource(IdArgumentsProvider.class)
    void Should_CreateNewProjection(Long id) {
        ProjectionCreationDto request = new ProjectionCreationDto();
        request.setMovieId(id);
        request.setTheaterId(id);

        Projection projection = ProjectionConstants.getSimpleProjection(id);
        ProjectionDto expected = ProjectionConstants.getSimpleProjectionDto(id);

        when(mockProjectionService.create(any())).thenReturn(projection);

        ProjectionDto actual = projectionController.create(request);

        assertAll(
                () -> assertNotNull(actual),
                () -> assertEquals(expected.getId(), actual.getId())
        );
    }

    @ParameterizedTest
    @ArgumentsSource(IdArgumentsProvider.class)
    void Should_DeleteProjectionWithGivenId(Long id) {
        Long expected = id;

        when(mockProjectionService.delete(any())).thenReturn(expected);

        Long actual = projectionController.delete(id);

        assertAll(
                () -> assertNotNull(actual),
                () -> assertEquals(expected, actual)
        );
    }

    @ParameterizedTest
    @ArgumentsSource(IdAndNameArgumentsProvider.class)
    void Should_ListAllProjections(Long id) {
        Projection projection = ProjectionConstants.getSimpleProjection(id);
        ProjectionDto projectionDto = ProjectionConstants.getSimpleProjectionDto(id);

        List<Projection> projections = List.of(projection);
        List<ProjectionDto> expected = List.of(projectionDto);

        when(mockProjectionService.findAll()).thenReturn(projections);

        List<ProjectionDto> actual = projectionController.findAll();

        assertAll(
                () -> assertNotNull(actual),
                () -> assertEquals(expected.size(), actual.size()),
                () -> assertEquals(expected.get(0).getId(), actual.get(0).getId())
        );
    }

    @ParameterizedTest
    @ArgumentsSource(IdAndNameArgumentsProvider.class)
    void Should_ListAllAvailableProjections(Long id) {
        Projection projection = ProjectionConstants.getSimpleProjection(id);
        ProjectionDto projectionDto = ProjectionConstants.getSimpleProjectionDto(id);

        List<Projection> projections = List.of(projection);
        List<ProjectionDto> expected = List.of(projectionDto);

        when(mockProjectionService.findAllAvailable()).thenReturn(projections);

        List<ProjectionDto> actual = projectionController.findAllAvailableProjections();

        assertAll(
                () -> assertNotNull(actual),
                () -> assertEquals(expected.size(), actual.size()),
                () -> assertEquals(expected.get(0).getId(), actual.get(0).getId())
        );
    }

    @ParameterizedTest
    @ArgumentsSource(IdAndNameArgumentsProvider.class)
    void Should_ReturnProjectionDtoWithGivenId(Long id) {
        Projection projection = ProjectionConstants.getSimpleProjection(id);
        ProjectionDto expected = ProjectionConstants.getSimpleProjectionDto(id);

        when(mockProjectionService.findById(any())).thenReturn(projection);

        ProjectionDto actual = projectionController.findById(id);

        assertAll(
                () -> assertNotNull(actual),
                () -> assertEquals(expected.getId(), actual.getId())
        );
    }

}
