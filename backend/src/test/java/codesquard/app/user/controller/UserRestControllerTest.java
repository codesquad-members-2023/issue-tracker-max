package codesquard.app.user.controller;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import codesquard.app.ControllerTestSupport;
import codesquard.app.errors.handler.GlobalExceptionHandler;
import codesquard.app.user.controller.request.UserSaveRequest;
import codesquard.app.user.service.request.UserSaveServiceRequest;
import codesquard.app.user.service.response.UserSaveResponse;

class UserRestControllerTest extends ControllerTestSupport {

	private MockMvc mockMvc;

	@BeforeEach
	void beforeEach() {
		mockMvc = MockMvcBuilders.standaloneSetup(new UserRestController(userService))
			.setControllerAdvice(new GlobalExceptionHandler())
			.build();
	}

	@Test
	@DisplayName("회원정보가 주어지고 회원가입 요청을 했을때 회원이 등록된다")
	public void createUser() throws Exception {
		// given
		UserSaveRequest userSaveRequest = new UserSaveRequest("hong1234", "hong1234@gmail.com", "hong1234", "hong1234");
		UserSaveResponse userSaveResponse = UserSaveResponse.success();
		// mocking
		when(userService.signUp(Mockito.any(UserSaveServiceRequest.class))).thenReturn(userSaveResponse);
		// when then
		mockMvc.perform(post("/api/users")
				.content(objectMapper.writeValueAsString(userSaveRequest))
				.contentType(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isCreated())
			.andExpect(jsonPath("success").value(equalTo(true)));
	}

	@Test
	@DisplayName("loginId가 공백인 경우 에러가 응답됩니다.")
	public void createUser_invalidLoginId_response400() throws Exception {
		// given
		UserSaveRequest userSaveRequest = new UserSaveRequest("", "hong1234@gmail.com", "hong1234", "hong1234");
		UserSaveResponse userSaveResponse = UserSaveResponse.success();
		// mocking
		when(userService.signUp(Mockito.any(UserSaveServiceRequest.class))).thenReturn(userSaveResponse);
		// when then
		mockMvc.perform(post("/api/users")
				.content(objectMapper.writeValueAsString(userSaveRequest))
				.contentType(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("success").value(equalTo(false)))
			.andExpect(jsonPath("errorCode.name").value(equalTo("INVALID_INPUT_FORMAT")))
			.andExpect(jsonPath("errorCode.code").value(equalTo(400)))
			.andExpect(jsonPath("errorCode.httpStatus").value(equalTo("BAD_REQUEST")))
			.andExpect(jsonPath("errorCode.errorMessage").value(equalTo("유효하지 않은 입력 형식입니다.")))
			.andExpect(jsonPath("errorCode.errors[0].field").value(equalTo("loginId")))
			.andExpect(jsonPath("errorCode.errors[0].message").value(equalTo("영문자 대소문자, 숫자를 사용하여 5~20자 이내여야 합니다.")));
	}

	@DisplayName("유효하지 않은 이메일이 주어지고 회원가입을 요청할때 에러를 응답합니다.")
	@ParameterizedTest
	@MethodSource("provideInvalidEmail")
	public void givenInvalidEmail_whenCreateUser_thenResponse400(String email) throws Exception {
		// given
		UserSaveRequest userSaveRequest = new UserSaveRequest("hong1234", email, "hong1234", "hong1234");
		UserSaveResponse userSaveResponse = UserSaveResponse.success();
		// mocking
		when(userService.signUp(Mockito.any(UserSaveServiceRequest.class))).thenReturn(userSaveResponse);
		// when then
		mockMvc.perform(post("/api/users")
				.content(objectMapper.writeValueAsString(userSaveRequest))
				.contentType(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("success").value(equalTo(false)))
			.andExpect(jsonPath("errorCode.name").value(equalTo("INVALID_INPUT_FORMAT")))
			.andExpect(jsonPath("errorCode.code").value(equalTo(400)))
			.andExpect(jsonPath("errorCode.httpStatus").value(equalTo("BAD_REQUEST")))
			.andExpect(jsonPath("errorCode.errorMessage").value(equalTo("유효하지 않은 입력 형식입니다.")))
			.andExpect(jsonPath("errorCode.errors[0].field").value(equalTo("email")))
			.andExpect(jsonPath("errorCode.errors[0].message").value(equalTo("이메일은 '사용자명@도메인주소' 형식이어야 합니다.")));
	}

	private static Stream<Arguments> provideInvalidEmail() {
		return Stream.of(
			Arguments.of(""),
			Arguments.of(" "),
			Arguments.of("hong1234"),
			Arguments.of("hong1234@"),
			Arguments.of("hong1234@.com")
		);
	}

	@DisplayName("유효하지 않은 비밀번호가 주어지고 회원가입을 요청할때 에러를 응답합니다.")
	@ParameterizedTest
	@MethodSource("provideInvalidPassword")
	public void givenInvalidPassword_whenCreateUser_thenResponse400(String password) throws Exception {
		// given
		UserSaveRequest userSaveRequest = new UserSaveRequest("hong1234", "hong1234@gmail.com", password, "hong1234");
		UserSaveResponse userSaveResponse = UserSaveResponse.success();
		// mocking
		when(userService.signUp(Mockito.any(UserSaveServiceRequest.class))).thenReturn(userSaveResponse);
		// when then
		mockMvc.perform(post("/api/users")
				.content(objectMapper.writeValueAsString(userSaveRequest))
				.contentType(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("success").value(equalTo(false)))
			.andExpect(jsonPath("errorCode.name").value(equalTo("INVALID_INPUT_FORMAT")))
			.andExpect(jsonPath("errorCode.code").value(equalTo(400)))
			.andExpect(jsonPath("errorCode.httpStatus").value(equalTo("BAD_REQUEST")))
			.andExpect(jsonPath("errorCode.errorMessage").value(equalTo("유효하지 않은 입력 형식입니다.")))
			.andExpect(jsonPath("errorCode.errors[0].field").value(equalTo("password")))
			.andExpect(jsonPath("errorCode.errors[0].message").value(equalTo("8~16자 영문 대 소문자, 숫자, 특수문자만 사용 가능합니다.")));
	}

	private static Stream<Arguments> provideInvalidPassword() {
		return Stream.of(
			Arguments.of(""),
			Arguments.of(" "),
			Arguments.of("hong"),
			Arguments.of("honghonghonghonghonghonghonghong")
		);
	}
}
