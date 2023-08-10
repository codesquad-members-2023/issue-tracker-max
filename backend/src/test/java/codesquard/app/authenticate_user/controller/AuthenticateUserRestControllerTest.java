package codesquard.app.authenticate_user.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import javax.servlet.http.Cookie;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import codesquard.app.ControllerTestSupport;
import codesquard.app.api.errors.errorcode.JwtTokenErrorCode;
import codesquard.app.api.errors.exception.jwt.JwtRestApiException;
import codesquard.app.api.errors.handler.GlobalExceptionHandler;
import codesquard.app.authenticate_user.service.request.RefreshTokenServiceRequest;
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
		// mockingSDFDAWAWE]F
		String mockAccessToken = "eyJhbGciOiJIUzM4NCJ9.eyJhdXRoZW50aWNhdGVVc2VyIjoie1wiaWRcIjoyLFwibG9naW5JZFwiOlwiaG9uZzEyMzRcIixcImVtYWlsXCI6XCJob25nMTIzNEBuYXZlci5jb21cIixcImF2YXRhclVybFwiOm51bGx9IiwiZXhwIjoxNjkxMDQ3NjMzfQ.DjYOw01cTrYDPYj0yZ4vYHdzpEPNnfyGn6BRsNzaYH2uPfYQb7XO4IO-fBDZhyiO";
		String mockRefreshToken = "eyJhbGciOiJIUzM4NCJ9.eyJleHAiOjE2OTM2MzkzMzN9.C9MoQ_FzoPLHnzD-MlE68H5Ifcl0wjIZa9eH8hYnw5qozbU-X5kQg-LXZ5HiWIY-";
		when(authenticateUserService.refreshToken(any(RefreshTokenServiceRequest.class)))
			.thenReturn(new Jwt(mockAccessToken, mockRefreshToken));
		// when & then
		mockMvc.perform(post("/api/auth/refresh/token")
				.cookie(new Cookie("refreshToken", refreshToken)))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("code").value(Matchers.equalTo(200)))
			.andExpect(jsonPath("status").value(Matchers.equalTo("OK")))
			.andExpect(jsonPath("message").value(Matchers.equalTo("Refreshtoken이 성공적으로 갱신되었습니다.")))
			.andExpect(jsonPath("data.jwt.accessToken").value(Matchers.equalTo(mockAccessToken)))
			.andExpect(jsonPath("data.jwt.refreshToken").value(Matchers.equalTo(mockRefreshToken)));
	}

	@Test
	@DisplayName("불일치하는 refreshToken이 주어지고 refreshToken 갱신을 요청할때 에러를 응답한다")
	public void givenNotMatchRefreshToken_whenRefreshToken_thenResponse400() throws Exception {
		// given
		String refreshToken = "";
		// mocking
		when(authenticateUserService.refreshToken(any(RefreshTokenServiceRequest.class)))
			.thenThrow(new JwtRestApiException(JwtTokenErrorCode.NOT_MATCH_REFRESHTOKEN));
		// when & then
		mockMvc.perform(post("/api/auth/refresh/token")
				.cookie(new Cookie("refreshToken", refreshToken)))
			.andDo(print())
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("code").value(Matchers.equalTo(400)))
			.andExpect(jsonPath("status").value(Matchers.equalTo("BAD_REQUEST")))
			.andExpect(jsonPath("message").value(Matchers.equalTo("Refreshtoken이 일치하지 않습니다.")))
			.andExpect(jsonPath("data").value(Matchers.equalTo(null)));
	}

}
