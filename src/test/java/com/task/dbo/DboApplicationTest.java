package com.task.dbo;

import org.junit.ClassRule;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.PostgreSQLContainer;

@SpringBootTest
class DboApplicationTest {

	@ClassRule
	public static PostgreSQLContainer<PostgresqlTc> postgreSQLContainer = PostgresqlTc.getInstance();

	@Test
	void contextLoads() {
	}

}
