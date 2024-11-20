package com.task.dbo.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NotFoundException extends RuntimeException {

  private final String message;

  public static NotFoundException of(String message) {
    return new NotFoundException(message);
  }
}