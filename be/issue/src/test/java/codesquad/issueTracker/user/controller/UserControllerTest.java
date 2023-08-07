package codesquad.issueTracker.user.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import codesquad.issueTracker.user.dto.LoginRequestDto;
import codesquad.issueTracker.user.dto.SignUpRequestDto;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Sql(scripts = {"classpath:schema/schema.sql"})
class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private WebApplicationContext context;

	@BeforeEach
	public void setMockMvc() throws Exception {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

		SignUpRequestDto signUpRequestDto = new SignUpRequestDto("asd123@ddd.com", "12345678", "sadfadsf");
		String request = objectMapper.writeValueAsString(signUpRequestDto);
		mockMvc.perform(post("/api/signup")
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.content(request));
	}

	@Test
	void loginTest() throws Exception {
		LoginRequestDto loginRequestDto = new LoginRequestDto("asd123@ddd.com", "12345678");

		String request = objectMapper.writeValueAsString(loginRequestDto);

		ResultActions resultActions = mockMvc.perform(post("/api/login")
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.content(request));

		// System.out.println(jsonPath("$[0].message.accessToken").exists());
		resultActions.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(jsonPath("$.message.accessToken").exists());
	}
}