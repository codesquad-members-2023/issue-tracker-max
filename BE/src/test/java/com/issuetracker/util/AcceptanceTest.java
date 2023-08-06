package com.issuetracker.util;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import io.restassured.RestAssured;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public abstract class AcceptanceTest {

	@LocalServerPort
	private int port;

	@Autowired
	private DatabaseInitialization databaseInitialization;

	@BeforeEach
	void setUp() {
		RestAssured.port = port;
		databaseInitialization.initialization();
		databaseInitialization.loadData();
	}
}
