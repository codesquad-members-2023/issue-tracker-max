package codesquard.app.user.controller;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import codesquard.app.ControllerTestSupport;
import codesquard.app.errors.errorcode.UserErrorCode;
import codesquard.app.errors.exception.RestApiException;
import codesquard.app.errors.handler.GlobalExceptionHandler;
import codesquard.app.jwt.filter.VerifyUserFilter;
import codesquard.app.user.controller.request.UserLoginRequest;
import codesquard.app.user.entity.AuthenticateUser;
import codesquard.app.user.service.request.UserLoginServiceRequest;

class UserLoginRestControllerTest extends ControllerTestSupport {

	private MockMvc mockMvc;

	@BeforeEach
	void beforeEach() {
		mockMvc = MockMvcBuilders.standaloneSetup(new UserLoginRestController(jwtProvider, objectMapper, userService))
			.setControllerAdvice(new GlobalExceptionHandler())
			.addFilter(new VerifyUserFilter(objectMapper, userService))
			.build();
	}

	@Test
	@DisplayName("로그인 아이디와 비밀번호가 주어지고 로그인 요청을 하면 로그인한 사용자의 정보를 응답합니다.")
	public void login_givenLoginInfo_whenLogin_thenUserLoginResponse() throws Exception {
		// given
		UserLoginRequest userLoginRequest = new UserLoginRequest("hong1234", "hong1234");
		AuthenticateUser mockAuthenticateUser = new AuthenticateUser(1L, "hong1234", "hong1234@gmail.com", null);
		// mocking
		when(userService.verifyUser(Mockito.any(UserLoginServiceRequest.class))).thenReturn(mockAuthenticateUser);
		// when & then
		mockMvc.perform(post("/api/login")
				.content(objectMapper.writeValueAsString(userLoginRequest))
				.contentType(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("id").value(equalTo(1)))
			.andExpect(jsonPath("loginId").value(equalTo("hong1234")))
			.andExpect(jsonPath("email").value(equalTo("hong1234@gmail.com")))
			.andExpect(jsonPath("avatarUrl").value(equalTo(null)))
			.andExpect(header().exists("Authorization"))
			.andExpect(cookie().exists("refreshToken"));
	}

	@Test
	@DisplayName("유효하지 않은 입력 형식의 로그인 아이디가 주어지고 로그인을 요청했을때 에러를 응답합니다.")
	public void login_givenInvalidLoginId_whenLogin_thenResponseError() throws Exception {
		// given
		UserLoginRequest userLoginRequest = new UserLoginRequest("", "hong1234");
		// mocking
		when(userService.verifyUser(Mockito.any(UserLoginServiceRequest.class)))
			.thenThrow(new RestApiException(UserErrorCode.NOT_FOUND_USER));
		// when & then
		mockMvc.perform(post("/api/login")
				.content(objectMapper.writeValueAsString(userLoginRequest))
				.contentType(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("success").value(equalTo(false)))
			.andExpect(jsonPath("errorCode.name").value(equalTo("NOT_MATCH_LOGIN")))
			.andExpect(jsonPath("errorCode.code").value(equalTo(400)))
			.andExpect(jsonPath("errorCode.httpStatus").value(equalTo("BAD_REQUEST")))
			.andExpect(jsonPath("errorCode.errorMessage").value(equalTo("아이디 또는 비밀번호가 일치하지 않습니다.")));
	}
}
