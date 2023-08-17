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
import codesquard.app.api.errors.errorcode.UserErrorCode;
import codesquard.app.api.errors.exception.RestApiException;
import codesquard.app.api.errors.handler.GlobalExceptionHandler;
import codesquard.app.user.controller.request.UserSaveRequest;
import codesquard.app.user.fixture.FixedUserFactory;
import codesquard.app.user.service.request.UserSaveServiceRequest;
import codesquard.app.user.service.response.UserSaveServiceResponse;

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
		UserSaveRequest request = FixedUserFactory.userSaveRequest();
		UserSaveServiceResponse serviceResponse = FixedUserFactory.userSaveServiceResponse();
		// mocking
		when(userService.signUp(Mockito.any())).thenReturn(serviceResponse);
		// when then
		mockMvc.perform(post("/api/users")
				.content(objectMapper.writeValueAsString(request))
				.contentType(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isCreated())
			.andExpect(jsonPath("code").value(equalTo(201)))
			.andExpect(jsonPath("status").value(equalTo("CREATED")))
			.andExpect(jsonPath("message").value(equalTo("회원가입에 성공하였습니다.")))
			.andExpect(jsonPath("data.id").value(equalTo(1)))
			.andExpect(jsonPath("data.loginId").value(equalTo("hong1234")))
			.andExpect(jsonPath("data.email").value(equalTo("hong1234@gmail.com")));
	}

	@Test
	@DisplayName("loginId가 공백인 경우 에러가 응답됩니다.")
	public void createUser_invalidLoginId_response400() throws Exception {
		// given
		UserSaveRequest request = FixedUserFactory.emptyLoginIdUserSaveRequest();
		// when then
		mockMvc.perform(post("/api/users")
				.content(objectMapper.writeValueAsString(request))
				.contentType(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("code").value(equalTo(400)))
			.andExpect(jsonPath("status").value(equalTo("BAD_REQUEST")))
			.andExpect(jsonPath("message").value(equalTo("Bad Request")))
			.andExpect(jsonPath("data[0].field").value(equalTo("loginId")))
			.andExpect(jsonPath("data[0].defaultMessage").value(equalTo("영문자 대소문자, 숫자를 사용하여 5~20자 이내여야 합니다.")));
	}

	@DisplayName("유효하지 않은 이메일이 주어지고 회원가입을 요청할때 에러를 응답합니다.")
	@ParameterizedTest
	@MethodSource("provideInvalidEmail")
	public void givenInvalidEmail_whenCreateUser_thenResponse400(String email) throws Exception {
		// given
		UserSaveRequest request = FixedUserFactory.invalidEmailUserSaveRequest(email);
		// when then
		mockMvc.perform(post("/api/users")
				.content(objectMapper.writeValueAsString(request))
				.contentType(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("code").value(equalTo(400)))
			.andExpect(jsonPath("status").value(equalTo("BAD_REQUEST")))
			.andExpect(jsonPath("message").value(equalTo("Bad Request")))
			.andExpect(jsonPath("data[0].field").value(equalTo("email")))
			.andExpect(jsonPath("data[0].defaultMessage").value(equalTo("이메일은 '사용자명@도메인주소' 형식이어야 합니다.")));
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
		UserSaveRequest request = FixedUserFactory.invalidPasswordUserSaveRequest(password);
		// when then
		mockMvc.perform(post("/api/users")
				.content(objectMapper.writeValueAsString(request))
				.contentType(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("code").value(equalTo(400)))
			.andExpect(jsonPath("status").value(equalTo("BAD_REQUEST")))
			.andExpect(jsonPath("message").value(equalTo("Bad Request")))
			.andExpect(jsonPath("data[0].field").value(equalTo("password")))
			.andExpect(jsonPath("data[0].defaultMessage").value(equalTo("8~16자 영문 대 소문자, 숫자, 특수문자만 사용 가능합니다.")));
	}

	@DisplayName("유효하지 않은 비밀번호와 비밀번호 확인이 주어지고 회원가입을 요청할때 에러를 응답합니다.")
	@ParameterizedTest
	@MethodSource("provideInvalidPassword")
	public void givenInvalidPasswordAndPasswordConfirm_whenCreateUser_thenResponse400(String password) throws
		Exception {
		// given
		UserSaveRequest request = FixedUserFactory.invalidPasswordAndPasswordConfirmUserSaveRequest(password, password);
		// when then
		mockMvc.perform(post("/api/users")
				.content(objectMapper.writeValueAsString(request))
				.contentType(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("code").value(equalTo(400)))
			.andExpect(jsonPath("status").value(equalTo("BAD_REQUEST")))
			.andExpect(jsonPath("message").value(equalTo("Bad Request")))
			.andExpect(jsonPath("data[*].field", containsInAnyOrder("password", "passwordConfirm")))
			.andExpect(jsonPath("data[*].defaultMessage", hasItem("8~16자 영문 대 소문자, 숫자, 특수문자만 사용 가능합니다.")));
	}

	private static Stream<Arguments> provideInvalidPassword() {
		return Stream.of(
			Arguments.of(""),
			Arguments.of(" "),
			Arguments.of("hong"),
			Arguments.of("honghonghonghonghonghonghonghong")
		);
	}

	@DisplayName("유효하지 않은 로그인 아이디와 이메일이 주어지고 회원가입을 요청할때 에러를 응답합니다.")
	@Test
	public void givenInvalidLoginIdAndEmail_whenCreateUser_thenResponse400() throws Exception {
		// given
		UserSaveRequest request = FixedUserFactory.invalidLoginIdAndEmailUserSaveRequest("", "");
		// when then
		mockMvc.perform(post("/api/users")
				.content(objectMapper.writeValueAsString(request))
				.contentType(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("code").value(equalTo(400)))
			.andExpect(jsonPath("status").value(equalTo("BAD_REQUEST")))
			.andExpect(jsonPath("message").value(equalTo("Bad Request")))
			.andExpect(jsonPath("data[*].field", containsInAnyOrder("loginId", "email")))
			.andExpect(jsonPath("data[*].defaultMessage", hasItem("영문자 대소문자, 숫자를 사용하여 5~20자 이내여야 합니다.")))
			.andExpect(jsonPath("data[*].defaultMessage", hasItem("이메일은 '사용자명@도메인주소' 형식이어야 합니다.")));
	}

	@Test
	@DisplayName("서로 다른 비밀번호와 비밀번호확인이 주어지고 회원가입 요청시 에러 응답을 받는다")
	public void givenDifferentPassword_whenCreateUser_thenResponse400() throws Exception {
		// given
		UserSaveRequest request = FixedUserFactory.passwordNotMatchedUserSaveRequest();
		// mocking
		when(userService.signUp(Mockito.any(UserSaveServiceRequest.class)))
			.thenThrow(new RestApiException(UserErrorCode.NOT_MATCH_PASSWORD));
		// when then
		mockMvc.perform(post("/api/users")
				.content(objectMapper.writeValueAsString(request))
				.contentType(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("code").value(equalTo(400)))
			.andExpect(jsonPath("status").value(equalTo("BAD_REQUEST")))
			.andExpect(jsonPath("message").value(equalTo("비밀번호가 일치하지 않습니다.")));
	}

	@Test
	@DisplayName("중복된 로그인 아이디가 주어지고 회원가입 요청시 에러 응답을 받는다")
	public void givenDuplicateLoginId_whenCreateUser_thenResponse400() throws Exception {
		// given
		UserSaveRequest request = FixedUserFactory.userSaveRequest();
		// mocking
		when(userService.signUp(Mockito.any())).thenThrow(new RestApiException(UserErrorCode.ALREADY_EXIST_LOGINID));
		// when then
		mockMvc.perform(post("/api/users")
				.content(objectMapper.writeValueAsString(request))
				.contentType(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isConflict())
			.andExpect(jsonPath("code").value(equalTo(409)))
			.andExpect(jsonPath("status").value(equalTo("CONFLICT")))
			.andExpect(jsonPath("message").value(equalTo("이미 존재하는 아이디입니다.")));
	}

	@Test
	@DisplayName("중복된 이메일이 주어지고 회원가입 요청시 에러 응답을 받는다")
	public void givenDuplicateEmail_whenCreateUser_thenResponse400() throws Exception {
		// given
		UserSaveRequest request = FixedUserFactory.userSaveRequest();
		// mocking
		when(userService.signUp(Mockito.any())).thenThrow(new RestApiException(UserErrorCode.ALREADY_EXIST_EMAIL));
		// when then
		mockMvc.perform(post("/api/users")
				.content(objectMapper.writeValueAsString(request))
				.contentType(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isConflict())
			.andExpect(jsonPath("code").value(equalTo(409)))
			.andExpect(jsonPath("status").value(equalTo("CONFLICT")))
			.andExpect(jsonPath("message").value(equalTo("이미 존재하는 이메일입니다.")));
	}

}
