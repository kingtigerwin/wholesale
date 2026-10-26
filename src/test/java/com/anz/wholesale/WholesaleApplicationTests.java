package com.anz.wholesale;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class WholesaleApplicationTests {

	@Autowired
	private DataSource dataSource;

	@Test
	void contextLoads() {
	}

	// Unit tests must stay on in-memory H2 (src/test/resources/application.properties); PostgreSQL is for integration tests.
	@Test
	void shouldUseH2GivenUnitTestConfiguration() throws SQLException {
		try (Connection connection = dataSource.getConnection()) {
			assertEquals("H2", connection.getMetaData().getDatabaseProductName());
		}
	}

}
