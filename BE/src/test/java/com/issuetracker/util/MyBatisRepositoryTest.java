package com.issuetracker.util;

import org.junit.jupiter.api.BeforeEach;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

@MybatisTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(value = "classpath:schema.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
public class MyBatisRepositoryTest extends MySqlContainer {

	private DatabaseLoader databaseLoader;

	@Autowired
	public MyBatisRepositoryTest(JdbcTemplate jdbcTemplate) {
		this.databaseLoader = new DatabaseLoader(jdbcTemplate);
	}

	@BeforeEach
	void setUp() {
		databaseLoader.loadData();
	}
}
