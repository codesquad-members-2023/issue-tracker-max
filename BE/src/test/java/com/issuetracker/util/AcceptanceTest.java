package com.issuetracker.util;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Sql(value = "classpath:schema.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
public abstract class AcceptanceTest extends MySqlContainer {

	@LocalServerPort
	private int port;

	@Autowired
	private DatabaseLoader databaseLoader;

	@BeforeEach
	void setUp() {
		RestAssured.port = port;
		databaseLoader.loadData();
	}

	protected void 응답_상태코드_검증(ExtractableResponse<Response> response, HttpStatus httpStatus) {
		assertThat(response.statusCode()).isEqualTo(httpStatus.value());
	}
}
