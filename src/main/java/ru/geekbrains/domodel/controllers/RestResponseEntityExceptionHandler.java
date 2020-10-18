package ru.geekbrains.domodel.controllers;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.geekbrains.domodel.dto.ErrorDto;
import ru.geekbrains.domodel.exceptions.EntityBadRequestException;
import ru.geekbrains.domodel.exceptions.EntityNotFoundException;

import static ru.geekbrains.domodel.entities.constants.Messages.*;

/**
 * Контроллер - глобальный обработчик исключений
 */
@Log4j2
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(
            TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.debug(String.format(INVALID_VALUE, "", ex.getValue()));
        String message = String.format(INVALID_VALUE, "", ex.getValue());
        return new ResponseEntity<>(new ErrorDto(400, message), HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        StringBuilder stringBuilder = new StringBuilder();
        ex.getBindingResult().getAllErrors()
                .forEach((error) -> stringBuilder.append(error.getDefaultMessage()).append(". "));
        log.debug(stringBuilder.toString());
        return new ResponseEntity<>(new ErrorDto(400, stringBuilder.toString()), HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleBindException(
            BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        FieldError fieldError = ex.getFieldError();
        String message = fieldError != null ?
                String.format(INVALID_VALUE, fieldError.getField(), fieldError.getRejectedValue()) : BAD_REQUEST;
        log.debug(message);
        return new ResponseEntity<>(new ErrorDto(400, message), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ IllegalArgumentException.class })
    public ResponseEntity<ErrorDto> handleIllegalArgument(Exception ex, WebRequest request) {
        log.debug(ex.getMessage());
        return new ResponseEntity<>(new ErrorDto(400, ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ IllegalStateException.class })
    public ResponseEntity<ErrorDto> handIllegalState(Exception ex, WebRequest request) {
        log.debug(ex.getMessage());
        return new ResponseEntity<>(new ErrorDto(400, ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ AccessDeniedException.class })
    public ResponseEntity<ErrorDto> handleAccessDenied(Exception ex, WebRequest request) {
        log.debug(ACCESS_DENIED);
        return new ResponseEntity<>(new ErrorDto(403, ACCESS_DENIED), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({ UsernameNotFoundException.class, BadCredentialsException.class})
    public ResponseEntity<ErrorDto> handleUsernameNotFound(Exception ex, WebRequest request) {
        log.debug(ex.getMessage());
        return new ResponseEntity<>(new ErrorDto(400, ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ EntityNotFoundException.class })
    public ResponseEntity<ErrorDto> handleEntityNotFound(Exception ex, WebRequest request) {
        log.debug(ex.getMessage());
        return new ResponseEntity<>(new ErrorDto(404, ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({ EntityBadRequestException.class })
    public ResponseEntity<ErrorDto> handleEntityBadRequest(Exception ex, WebRequest request) {
        log.debug(ex.getMessage());
        return new ResponseEntity<>(new ErrorDto(400, ex.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
