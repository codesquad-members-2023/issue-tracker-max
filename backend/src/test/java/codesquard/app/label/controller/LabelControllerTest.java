package codesquard.app.label.controller;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import codesquard.app.api.errors.handler.GlobalExceptionHandler;
import codesquard.app.authenticate_user.entity.AuthenticateUser;
import codesquard.app.label.dto.request.LabelSaveRequest;
import codesquard.app.label.dto.request.LabelUpdateRequest;
import codesquard.app.label_milestone.ControllerTestSupport;

class LabelControllerTest extends ControllerTestSupport {

	@DisplayName("라벨을 등록한다.")
	@Test
	void saveLabel() throws Exception {
		// given
		mockingAuthenticateUser();
		LabelSaveRequest labelSaveRequest = new LabelSaveRequest("이름", "light", "#040404", "설명");

		// when // then
		mockMvc.perform(post("/api/labels")
				.content(objectMapper.writeValueAsString(labelSaveRequest))
				.contentType(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isCreated());
	}

	@DisplayName("필수 항목을 입력하지 않고 라벨 등록 시 예외가 발생한다.")
	@MethodSource("provideSaveInvalidInputLabel")
	@ParameterizedTest
	void saveInvalidInputLabel(String name) throws Exception {
		// given
		mockingAuthenticateUser();
		LabelSaveRequest labelSaveRequest = new LabelSaveRequest(name, "light", "#040404", "설명");

		// when // then
		mockMvc.perform(post("/api/labels")
				.content(objectMapper.writeValueAsString(labelSaveRequest))
				.contentType(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.code").value("400"))
			.andExpect(jsonPath("$.status").value("BAD_REQUEST"))
			.andExpect(jsonPath("$.message").value("Bad Request"))
			.andExpect(jsonPath("$.data[*].field").value("name"))
			.andExpect(jsonPath("$.data[*].defaultMessage").value("제목 입력은 필수입니다."));
	}

	private static Stream<Arguments> provideSaveInvalidInputLabel() {
		return Stream.of(
			Arguments.of((Object)null)
		);
	}

	@DisplayName("라벨 입력 글자 수의 범위를 초과할 시 예외가 발생한다.")
	@MethodSource("provideSaveInvalidDescriptionLabel")
	@ParameterizedTest
	void saveInvalidScopeLabel(String name) throws Exception {
		// given
		mockingAuthenticateUser();
		LabelSaveRequest labelSaveRequest = new LabelSaveRequest(name, "light", "#040404", "설명");

		// when // then
		mockMvc.perform(post("/api/labels")
				.content(objectMapper.writeValueAsString(labelSaveRequest))
				.contentType(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.code").value("400"))
			.andExpect(jsonPath("$.status").value("BAD_REQUEST"))
			.andExpect(jsonPath("$.message").value("Bad Request"))
			.andExpect(jsonPath("$.data[*].field").value("name"))
			.andExpect(jsonPath("$.data[*].defaultMessage").value("제목은 1글자 이상, 20글자 이하여야 합니다."));
	}

	private static Stream<Arguments> provideSaveInvalidDescriptionLabel() {
		return Stream.of(
			Arguments.of("")
		);
	}

	@DisplayName("라벨을 수정한다.")
	@Test
	void updateLabel() throws Exception {
		// given
		mockingAuthenticateUser();
		LabelUpdateRequest labelUpdateRequest = new LabelUpdateRequest("바뀐 이름", "dark", "#111104", "바뀐 설명");

		// when // then
		mockMvc.perform(put("/api/labels/1")
				.content(objectMapper.writeValueAsString(labelUpdateRequest))
				.contentType(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isOk());
	}

	@DisplayName("라벨 수정 글자 수의 범위를 초과할 시 예외가 발생한다.")
	@MethodSource("provideUpdateInvalidDescriptionLabel")
	@ParameterizedTest
	void updateInvalidScopeLabel(String name) throws Exception {
		// given
		mockingAuthenticateUser();
		LabelUpdateRequest labelUpdateRequest = new LabelUpdateRequest(name, "light", "#040404", "설명");

		// when // then
		mockMvc.perform(put("/api/labels/1")
				.content(objectMapper.writeValueAsString(labelUpdateRequest))
				.contentType(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.code").value("400"))
			.andExpect(jsonPath("$.status").value("BAD_REQUEST"))
			.andExpect(jsonPath("$.message").value("Bad Request"))
			.andExpect(jsonPath("$.data[*].field").value("name"))
			.andExpect(jsonPath("$.data[*].defaultMessage").value("제목은 1글자 이상, 20글자 이하여야 합니다."));
	}

	private static Stream<Arguments> provideUpdateInvalidDescriptionLabel() {
		return Stream.of(
			Arguments.of("")
		);
	}

	@DisplayName("라벨을 삭제한다.")
	@Test
	void deleteLabel() throws Exception {
		// given
		mockingAuthenticateUser();

		// when // then
		mockMvc.perform(delete("/api/labels/1"))
			.andDo(print())
			.andExpect(status().isOk());
	}

	private void mockingAuthenticateUser() {
		mockMvc = MockMvcBuilders.standaloneSetup(new LabelController(labelService))
			.setControllerAdvice(new GlobalExceptionHandler())
			.setCustomArgumentResolvers(loginUserArgumentResolver)
			.build();

		AuthenticateUser user = new AuthenticateUser(1L, "user", "user@email.com", null);
		when(loginUserArgumentResolver.supportsParameter(any()))
			.thenReturn(true);
		when(loginUserArgumentResolver.resolveArgument(any(), any(), any(), any()))
			.thenReturn(user);
	}
}
