package com.example.edukan.controller;

import com.example.edukan.model.exception.ExceptionDto;
import com.example.edukan.model.exception.NotFoundException;
import com.example.edukan.model.exception.PasswordException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ExceptionDto handle(NotFoundException exception) {
        log.error("ActionLog.handle.error NotFoundException handled");
        return new ExceptionDto(exception.getMessage());
    }

    @ExceptionHandler(PasswordException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ExceptionDto handle(PasswordException exception) {
        log.error("ActionLog.handle.error PasswordException handled");
        return new ExceptionDto(exception.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    ExceptionDto handle(Exception exception) {
        log.error("ActionLog.handle.error Exception handled: {}", exception.getMessage());
        return new ExceptionDto("UNEXPECTED_ERROR");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    List<ExceptionDto> handle(MethodArgumentNotValidException exception) {
        log.error("ActionLog.handle.error Exception handled: {}", exception.getMessage());
        List<ExceptionDto> errors = new ArrayList<>();
        exception.getBindingResult().getFieldErrors().forEach(e -> errors.add(new ExceptionDto(e.getDefaultMessage())));
        return errors;
    }
}