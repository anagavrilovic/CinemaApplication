package com.example.cinema.exception;

import com.example.cinema.dto.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@RestControllerAdvice
public class RestResponseEntityExceptionHandler {

    @ExceptionHandler({ObjectAlreadyExistsException.class,
                       BusinessLogicException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ErrorDto handleBadRequestException(RuntimeException e) {
        return new ErrorDto(e.getMessage(), HttpStatus.BAD_REQUEST, LocalDateTime.now());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected ErrorDto handleNotFoundException(RuntimeException e) {
        return new ErrorDto(e.getMessage(), HttpStatus.NOT_FOUND, LocalDateTime.now());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleMethodValidationFailure(final MethodArgumentNotValidException ex) {
        StringBuilder b = new StringBuilder();
        BindingResult results = ex.getBindingResult();
        for (FieldError e: results.getFieldErrors()) {
            b.append(e.getDefaultMessage()).append("\n");
        }
        return b.toString();
    }

}
