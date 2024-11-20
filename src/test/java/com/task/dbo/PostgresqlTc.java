package com.task.dbo;

import org.testcontainers.containers.PostgreSQLContainer;

public class PostgresqlTc extends PostgreSQLContainer<PostgresqlTc> {
  private static final String IMAGE_VERSION = "postgres:16.4";
  private static PostgresqlTc container;

  private PostgresqlTc() {
    super(IMAGE_VERSION);
  }

  public static PostgresqlTc getInstance() {
    if (container == null) {
      container = new PostgresqlTc();
    }
    return container;
  }

  @Override
  public void start() {
    super.start();
    System.setProperty("DB_URL", container.getJdbcUrl());
    System.setProperty("DB_USERNAME", container.getUsername());
    System.setProperty("DB_PASSWORD", container.getPassword());
  }

  @Override
  public void stop() {
    //do nothing, JVM handles shut down
  }
}
