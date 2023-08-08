package codesquard.app.authenticate_user.controller;

import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import codesquard.app.ControllerTestSupport;
import codesquard.app.api.errors.handler.GlobalExceptionHandler;
import codesquard.app.authenticate_user.controller.request.RefreshTokenRequest;
import codesquard.app.authenticate_user.service.RefreshTokenServiceRequest;
import codesquard.app.jwt.Jwt;
import codesquard.app.jwt.filter.JwtAuthorizationFilter;

class AuthenticateUserRestControllerTest extends ControllerTestSupport {

	private MockMvc mockMvc;

	@BeforeEach
	void beforeEach() {
		mockMvc = MockMvcBuilders.standaloneSetup(
				new AuthenticateUserRestController(authenticateUserService))
			.setControllerAdvice(new GlobalExceptionHandler())
			.addFilter(new JwtAuthorizationFilter(jwtProvider, objectMapper))
			.build();
	}

	@Test
	@DisplayName("refreshToken이 주어지고 refreshToken 갱신을 요청할때 갱신되고 jwt 객체를 응답한다")
	public void refreshToken_givenRefreshToken_whenRefreshToken_thenResponseJwt() throws Exception {
		// given
		String refreshToken = "eyJhbGciOiJIUzM4NCJ9.eyJleHAiOjE2OTM2Mzk0Njl9.VTsTjBvNnS_D2xAj2uNwYlqQ9v5Re8tEQNuPuojTrud7Ybf7b1_wm9s1ypmhLbzS";
		RefreshTokenRequest refreshTokenRequest = new RefreshTokenRequest(refreshToken);
		// mocking
		String mockAccessToken = "eyJhbGciOiJIUzM4NCJ9.eyJhdXRoZW50aWNhdGVVc2VyIjoie1wiaWRcIjoyLFwibG9naW5JZFwiOlwiaG9uZzEyMzRcIixcImVtYWlsXCI6XCJob25nMTIzNEBuYXZlci5jb21cIixcImF2YXRhclVybFwiOm51bGx9IiwiZXhwIjoxNjkxMDQ3NjMzfQ.DjYOw01cTrYDPYj0yZ4vYHdzpEPNnfyGn6BRsNzaYH2uPfYQb7XO4IO-fBDZhyiO";
		String mockRefreshToken = "eyJhbGciOiJIUzM4NCJ9.eyJleHAiOjE2OTM2MzkzMzN9.C9MoQ_FzoPLHnzD-MlE68H5Ifcl0wjIZa9eH8hYnw5qozbU-X5kQg-LXZ5HiWIY-";
		when(authenticateUserService.refreshToken(any(RefreshTokenServiceRequest.class)))
			.thenReturn(new Jwt(mockAccessToken, mockRefreshToken));
		// when & then
		mockMvc.perform(post("/api/auth/refresh/token")
				.content(objectMapper.writeValueAsString(refreshTokenRequest))
				.contentType(APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("jwt.accessToken").value(Matchers.equalTo(mockAccessToken)))
			.andExpect(jsonPath("jwt.refreshToken").value(Matchers.equalTo(mockRefreshToken)));
	}

}
