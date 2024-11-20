package com.task.dbo.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BadRequestException extends RuntimeException {

  private final String message;

  public static BadRequestException of(String message) {
    return new BadRequestException(message);
  }
}