
package com.issuetracker.util;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

@JdbcTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(value = "classpath:schema.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
public class JdbcRepositoryTest extends MySqlContainer {

	private DatabaseLoader databaseLoader;

	@Autowired
	public JdbcRepositoryTest(JdbcTemplate jdbcTemplate) {
		this.databaseLoader = new DatabaseLoader(jdbcTemplate);
	}

	@BeforeEach
	void setUp() {
		databaseLoader.loadData();
	}
}
