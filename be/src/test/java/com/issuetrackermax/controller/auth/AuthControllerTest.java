package com.issuetrackermax.controller.auth;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import com.issuetrackermax.controller.ControllerTestSupport;
import com.issuetrackermax.controller.auth.dto.request.JwtRefreshTokenRequest;
import com.issuetrackermax.controller.auth.dto.request.LoginRequest;
import com.issuetrackermax.domain.jwt.entity.Jwt;

class AuthControllerTest extends ControllerTestSupport {

	@DisplayName("email, password를 입력하여 로그인을 성공한다.")
	@Test
	void login() throws Exception {
		// given
		String email = "june@codesquad.co.kr";
		String password = "12345678";
		String accessToken = "accessToken";
		String refreshToken = "refreshToken";
		Jwt jwt = new Jwt(accessToken, refreshToken);
		LoginRequest loginRequest = LoginRequest.builder()
			.loginId(email)
			.password(password)
			.build();

		// when
		when(jwtService.login(email, password)).thenReturn(jwt);

		// then
		mockMvc.perform(
				post("/api/signin")
					.content(objectMapper.writeValueAsString(loginRequest))
					.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.success").value("true"))
			.andExpect(jsonPath("$.data.accessToken").value(accessToken))
			.andExpect(jsonPath("$.data.refreshToken").value(refreshToken));

	}

	@DisplayName("accessToken의 기간이 지나 refreshToken을 통해 accessToken을 재발급받는다.")
	@Test
	void reissueAccessToken() throws Exception {
		// given
		String accessToken = "newAccessToken";
		String refreshToken = "refreshToken";
		Jwt jwt = new Jwt(accessToken, refreshToken);
		JwtRefreshTokenRequest jwtRefreshTokenRequest = JwtRefreshTokenRequest.builder()
			.refreshToken(refreshToken)
			.build();
		// when
		when(jwtService.reissueAccessToken(refreshToken)).thenReturn(jwt);

		// then
		mockMvc.perform(
				post("/api/reissue-access-token")
					.content(objectMapper.writeValueAsString(jwtRefreshTokenRequest))
					.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.success").value("true"))
			.andExpect(jsonPath("$.data.accessToken").value(accessToken))
			.andExpect(jsonPath("$.data.refreshToken").value(refreshToken));
	}
}
