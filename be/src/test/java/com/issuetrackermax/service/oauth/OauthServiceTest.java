package com.issuetrackermax.service.oauth;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.issuetrackermax.util.DatabaseCleaner;

@SpringBootTest
@ActiveProfiles("test")
class OauthServiceTest {

	@Autowired
	OauthService oauthService;

	@Autowired
	DatabaseCleaner databaseCleaner;

	@BeforeEach
	void setUp() {
		databaseCleaner.execute();
	}

	@DisplayName("")
	@Test
	void login() {

	}

}