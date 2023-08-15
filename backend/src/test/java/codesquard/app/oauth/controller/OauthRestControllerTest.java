package codesquard.app.oauth.controller;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import codesquard.app.ControllerTestSupport;
import codesquard.app.api.errors.errorcode.OauthErrorCode;
import codesquard.app.api.errors.exception.RestApiException;
import codesquard.app.api.errors.handler.GlobalExceptionHandler;
import codesquard.app.authenticate_user.entity.AuthenticateUser;
import codesquard.app.jwt.Jwt;
import codesquard.app.oauth.service.response.OauthLoginServiceResponse;

class OauthRestControllerTest extends ControllerTestSupport {

	private MockMvc mockMvc;

	@BeforeEach
	void beforeEach() {
		mockMvc = MockMvcBuilders.standaloneSetup(new OauthRestController(oauthService))
			.setControllerAdvice(new GlobalExceptionHandler())
			.build();
	}

	@Test
	@DisplayName("provider 이름과 인가 코드가 주어지고 github 소셜 로그인을 요청할때 로그인된 정보가 응답된다")
	public void login() throws Exception {
		// given
		String provider = "github";
		String authorizationCode = "c9a29e225ba316e155c2";
		AuthenticateUser user = new AuthenticateUser(1L, "hong1234", "hong1234@gmail.com", null);
		Jwt jwt = new Jwt("accessToken", "refreshToken");
		OauthLoginServiceResponse loginServiceResponse = new OauthLoginServiceResponse(user, jwt, "Bearer");
		// mocking
		Mockito.when(oauthService.login(Mockito.any(String.class), Mockito.any(String.class)))
			.thenReturn(loginServiceResponse);
		// when & then
		mockMvc.perform(post("/api/login/oauth/" + provider)
				.param("code", authorizationCode))
			.andDo(print())
			.andExpect(jsonPath("code").value(equalTo(200)))
			.andExpect(jsonPath("status").value(equalTo("OK")))
			.andExpect(jsonPath("message").value(equalTo("OK")))
			.andExpect(jsonPath("data.user.id").value(equalTo(1)))
			.andExpect(jsonPath("data.user.loginId").value(equalTo("hong1234")))
			.andExpect(jsonPath("data.user.email").value(equalTo("hong1234@gmail.com")))
			.andExpect(jsonPath("data.user.avatarUrl").value(equalTo(null)))
			.andExpect(jsonPath("data.jwt.accessToken").value(equalTo("accessToken")))
			.andExpect(jsonPath("data.jwt.refreshToken").value(equalTo("refreshToken")))
			.andExpect(jsonPath("data.tokenType").value(equalTo("Bearer")));
	}

	@Test
	@DisplayName("잘못된 인가 코드가 주어지고 github 소셜 로그인을 요청할때 에러를 응답합니다")
	public void login_givenInvalidAuthorizationCode_whenRequestOauthLoginApi_thenResponseError400() throws Exception {
		// given
		String provider = "github";
		String authorizationCode = "c9a29e225ba316e155c2";
		// mocking
		Mockito.when(oauthService.login(Mockito.any(String.class), Mockito.any(String.class)))
			.thenThrow(new RestApiException(OauthErrorCode.BAD_AUTHORIZATION_CODE));
		// when & then
		mockMvc.perform(post("/api/login/oauth/" + provider)
				.param("code", authorizationCode))
			.andDo(print())
			.andExpect(jsonPath("code").value(equalTo(400)))
			.andExpect(jsonPath("status").value(equalTo("BAD_REQUEST")))
			.andExpect(jsonPath("message").value(equalTo("잘못된 인가코드입니다.")))
			.andExpect(jsonPath("data").value(equalTo(null)));
	}
}
