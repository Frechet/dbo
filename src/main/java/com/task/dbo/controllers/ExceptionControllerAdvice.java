package com.task.dbo.controllers;

import com.task.dbo.exception.BadRequestException;
import com.task.dbo.exception.NotFoundException;
import com.task.dbo.dto.ExceptionResponseDto;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.http.ResponseEntity;

@Slf4j
@RestControllerAdvice
public class ExceptionControllerAdvice {

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<ExceptionResponseDto> handleClientException(NotFoundException e) {
    log.error(e.getMessage(), e);
    return ResponseEntity
        .status(HttpStatus.NOT_FOUND)
        .body(new ExceptionResponseDto(e.getMessage()));
  }

  @ExceptionHandler(BadRequestException.class)
  public ResponseEntity<ExceptionResponseDto> handleClientException(BadRequestException e) {
    log.error(e.getMessage(), e);
    return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(new ExceptionResponseDto(e.getMessage()));
  }

  /**
   * handler for the exception thrown by the  failing @Size, @Pattern or @Min/@Max
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ExceptionResponseDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
    log.error(e.getMessage(), e);
    return ResponseEntity
        .status(HttpStatus.NOT_ACCEPTABLE)
        .body(new ExceptionResponseDto(e.getMessage()));
  }

  /**
   * handler for the exception thrown by the wrong Enum value
   */
  @ExceptionHandler(InvalidFormatException.class)
  public ResponseEntity<ExceptionResponseDto> handleInvalidFormatException(InvalidFormatException e) {
    log.error(e.getMessage(), e);
    return ResponseEntity
        .status(HttpStatus.NOT_ACCEPTABLE)
        .body(new ExceptionResponseDto(e.getMessage()));
  }
}