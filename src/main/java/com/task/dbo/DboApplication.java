package com.task.dbo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@SpringBootApplication
@RequiredArgsConstructor
@Slf4j
public class DboApplication {

  public static void main(String[] args) {
    SpringApplication.run(DboApplication.class, args);
  }
}