package codesquard.app.authenticate_user.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

import codesquard.app.ControllerTestSupport;
import codesquard.app.api.errors.errorcode.JwtTokenErrorCode;
import codesquard.app.api.errors.exception.RestApiException;
import codesquard.app.api.errors.handler.GlobalExceptionHandler;
import codesquard.app.authenticate_user.entity.AuthenticateUser;
import codesquard.app.authenticate_user.resolver.LoginUserArgumentResolver;
import codesquard.app.authenticate_user.service.request.RefreshTokenServiceRequest;
import codesquard.app.jwt.Jwt;
import codesquard.app.jwt.filter.JwtAuthorizationFilter;
import codesquard.app.jwt.filter.VerifyUserFilter;

class AuthenticateUserRestControllerTest extends ControllerTestSupport {

	private MockMvc mockMvc;

	@Autowired
	private AuthenticateUserRestController authenticateUserRestController;

	@Mock
	private ValueOperations<String, Object> valueOperations;

	@Mock
	private LoginUserArgumentResolver loginUserArgumentResolver;

	@BeforeEach
	void beforeEach() {
		mockMvc = MockMvcBuilders.standaloneSetup(authenticateUserRestController)
			.setControllerAdvice(new GlobalExceptionHandler())
			.addFilter(new JwtAuthorizationFilter(jwtProvider, objectMapper, redisTemplate))
			.setCustomArgumentResolvers(loginUserArgumentResolver)
			.build();
	}

	@Test
	@DisplayName("refreshToken이 주어지고 refreshToken 갱신을 요청할때 갱신되고 jwt 객체를 응답한다")
	public void refreshToken_givenRefreshToken_whenRefreshToken_thenResponseJwt() throws Exception {
		// given
		Jwt jwt = new Jwt("accessToken", "refreshToken", null, null);
		//mokcing
		when(authenticateUserService.refreshToken(any(RefreshTokenServiceRequest.class)))
			.thenReturn(jwt);
		// when & then
		mockMvc.perform(post("/api/auth/refresh/token")
				.cookie(new Cookie("refreshToken", "refreshToken")))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("code").value(Matchers.equalTo(200)))
			.andExpect(jsonPath("status").value(Matchers.equalTo("OK")))
			.andExpect(jsonPath("message").value(Matchers.equalTo("Refreshtoken이 성공적으로 갱신되었습니다.")))
			.andExpect(jsonPath("data.jwt.accessToken").value(Matchers.equalTo("accessToken")))
			.andExpect(jsonPath("data.jwt.refreshToken").value(Matchers.equalTo("refreshToken")));
	}

	@Test
	@DisplayName("불일치하는 refreshToken이 주어지고 refreshToken 갱신을 요청할때 에러를 응답한다")
	public void givenNotMatchRefreshToken_whenRefreshToken_thenResponse400() throws Exception {
		// given
		String refreshToken = "";
		// mocking
		when(authenticateUserService.refreshToken(any(RefreshTokenServiceRequest.class)))
			.thenThrow(new RestApiException(JwtTokenErrorCode.NOT_MATCH_REFRESHTOKEN));
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

	@Test
	@DisplayName("인증 객체가 주어지고 로그아웃을 요청할때 로그아웃 된다")
	public void givenAuthenticateUser_whenRequestLogout_thenResponseOk() throws Exception {
		// given
		AuthenticateUser user = new AuthenticateUser(1L, "hong1234", "hong1234@gmail.com", null);
		Map<String, Object> claims = new HashMap<>();
		claims.put(VerifyUserFilter.AUTHENTICATE_USER, objectMapper.writeValueAsString(user));
		Jwt jwt = jwtProvider.createJwt(claims);
		// mocking
		when(redisTemplate.opsForValue()).thenReturn(valueOperations);
		when(valueOperations.get(anyString())).thenReturn("");
		when(loginUserArgumentResolver.supportsParameter((MethodParameter)any()))
			.thenReturn(true);
		when(loginUserArgumentResolver.resolveArgument(
			(MethodParameter)any()
			, (ModelAndViewContainer)any()
			, (NativeWebRequest)any()
			, (WebDataBinderFactory)any()
		)).thenReturn(user);
		// when && then
		mockMvc.perform(post("/api/auth/logout")
				.header("Authorization", jwt.createAccessTokenHeaderValue())
				.cookie(jwt.createRefreshTokenCookie()))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("code").value(Matchers.equalTo(200)))
			.andExpect(jsonPath("status").value(Matchers.equalTo("OK")))
			.andExpect(jsonPath("message").value(Matchers.equalTo("로그아웃이 완료되었습니다.")))
			.andExpect(jsonPath("data.id").value(Matchers.equalTo(1)))
			.andExpect(jsonPath("data.loginId").value(Matchers.equalTo("hong1234")))
			.andExpect(jsonPath("data.email").value(Matchers.equalTo("hong1234@gmail.com")))
			.andExpect(jsonPath("data.avatarUrl").value(Matchers.equalTo(null)));
	}
}
