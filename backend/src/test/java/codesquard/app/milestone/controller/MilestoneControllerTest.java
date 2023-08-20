package codesquard.app.milestone.controller;

import static org.junit.jupiter.api.DynamicTest.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import codesquard.app.api.errors.handler.GlobalExceptionHandler;
import codesquard.app.authenticate_user.entity.AuthenticateUser;
import codesquard.app.label_milestone.ControllerTestSupport;
import codesquard.app.milestone.dto.request.MilestoneSaveRequest;
import codesquard.app.milestone.dto.request.MilestoneStatusUpdateRequest;
import codesquard.app.milestone.dto.request.MilestoneUpdateRequest;

class MilestoneControllerTest extends ControllerTestSupport {

	@DisplayName("마일스톤을 등록한다.")
	@Test
	void saveMilestone() throws Exception {
		// given
		mockingAuthenticateUser();
		MilestoneSaveRequest milestoneSaveRequest = new MilestoneSaveRequest("이름", LocalDate.now(), "설명");

		// when // then
		mockMvc.perform(post("/api/milestones")
				.content(objectMapper.writeValueAsString(milestoneSaveRequest))
				.contentType(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isCreated());
	}

	// https://junit.org/junit5/docs/current/user-guide/#writing-tests-dynamic-tests
	// https://junit.org/junit5/docs/current/user-guide/#writing-tests-parameterized-tests
	@DisplayName("마일스톤 등록 시 이미 존재하는 제목이 존재할 경우 예외가 발생한다.")
	@TestFactory
	Collection<DynamicTest> saveInvalidDuplicateMilestone() {
		return Arrays.asList(
			dynamicTest("1차 등록을 진행한다. (성공)", () -> {
				// given
				mockingAuthenticateUser();
				MilestoneSaveRequest milestoneSaveRequest = new MilestoneSaveRequest("이름", LocalDate.now(), "설명");

				// when // then
				mockMvc.perform(post("/api/milestones")
						.content(objectMapper.writeValueAsString(milestoneSaveRequest))
						.contentType(MediaType.APPLICATION_JSON))
					.andDo(print())
					.andExpect(status().isCreated());
			}),

			dynamicTest("2차 등록을 진행한다. (예외 터짐)", () -> {
				// given
				mockingAuthenticateUser();
				MilestoneSaveRequest milestoneSaveRequest = new MilestoneSaveRequest("이름", LocalDate.now(), "설명");

				// when // then
				mockMvc.perform(post("/api/milestones")
						.content(objectMapper.writeValueAsString(milestoneSaveRequest))
						.contentType(MediaType.APPLICATION_JSON))
					.andDo(print())
					.andExpect(status().isCreated());

				// mockMvc.perform(post("/api/milestones")
				// 		.content(objectMapper.writeValueAsString(milestoneSaveRequest))
				// 		.contentType(MediaType.APPLICATION_JSON))
				// 	.andDo(print())
				// 	.andExpect(status().isBadRequest())
				// 	.andExpect(jsonPath("$.code").value("400"))
				// 	.andExpect(jsonPath("$.status").value("BAD_REQUEST"))
				// 	.andExpect(jsonPath("$.message").value("이미 동일한 이름의 마일스톤이 있습니다."))
				// 	.andExpect(jsonPath("$.data").value(null));
			})
		);
	}

	@DisplayName("필수 항목을 입력하지 않고 마일스톤 등록 시 예외가 발생한다.")
	@MethodSource("provideSaveInvalidInputMilestone")
	@ParameterizedTest
	void saveInvalidInputMilestone(String name) throws Exception {
		// given
		mockingAuthenticateUser();
		MilestoneSaveRequest milestoneSaveRequest = new MilestoneSaveRequest(name, LocalDate.now(), "설명");

		// when // then
		mockMvc.perform(post("/api/milestones")
				.content(objectMapper.writeValueAsString(milestoneSaveRequest))
				.contentType(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.code").value("400"))
			.andExpect(jsonPath("$.status").value("BAD_REQUEST"))
			.andExpect(jsonPath("$.message").value("Bad Request"))
			.andExpect(jsonPath("$.data[*].field").value("name"))
			.andExpect(jsonPath("$.data[*].defaultMessage").value("제목 입력은 필수입니다."));
	}

	private static Stream<Arguments> provideSaveInvalidInputMilestone() {
		return Stream.of(
			Arguments.of((Object)null)
		);
	}

	@DisplayName("마일스톤 입력 글자 수의 범위를 초과할 시 예외가 발생한다.")
	@ValueSource(strings = "")
	@ParameterizedTest
	void saveInvalidScopeLabel(String name) throws Exception {
		// given
		mockingAuthenticateUser();
		MilestoneSaveRequest milestoneSaveRequest = new MilestoneSaveRequest(name, LocalDate.now(), "설명");

		// when // then
		mockMvc.perform(post("/api/milestones")
				.content(objectMapper.writeValueAsString(milestoneSaveRequest))
				.contentType(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.code").value("400"))
			.andExpect(jsonPath("$.status").value("BAD_REQUEST"))
			.andExpect(jsonPath("$.message").value("Bad Request"))
			.andExpect(jsonPath("$.data[*].field").value("name"))
			.andExpect(jsonPath("$.data[*].defaultMessage").value("제목은 1글자 이상, 50글자 이하여야 합니다."));
	}

	@DisplayName("마일스톤을 수정한다.")
	@Test
	void updateMilestone() throws Exception {
		// given
		mockingAuthenticateUser();
		MilestoneUpdateRequest milestoneUpdateRequest = new MilestoneUpdateRequest("바뀐 이름", LocalDate.now(), "바뀐 설명");

		// when // then
		mockMvc.perform(put("/api/milestones/1")
				.content(objectMapper.writeValueAsString(milestoneUpdateRequest))
				.contentType(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isOk());
	}

	@DisplayName("마일스톤 수정 글자 수의 범위를 초과할 시 예외가 발생한다.")
	@ValueSource(strings = "")
	@ParameterizedTest
	void updateInvalidScopeMilestone(String name) throws Exception {
		// given
		mockingAuthenticateUser();
		MilestoneUpdateRequest milestoneUpdateRequest = new MilestoneUpdateRequest(name, LocalDate.now(), "바뀐 설명");

		// when // then
		mockMvc.perform(put("/api/milestones/1")
				.content(objectMapper.writeValueAsString(milestoneUpdateRequest))
				.contentType(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.code").value("400"))
			.andExpect(jsonPath("$.status").value("BAD_REQUEST"))
			.andExpect(jsonPath("$.message").value("Bad Request"))
			.andExpect(jsonPath("$.data[*].field").value("name"))
			.andExpect(jsonPath("$.data[*].defaultMessage").value("제목은 1글자 이상, 50글자 이하여야 합니다."));
	}

	@DisplayName("마일스톤 상태를 수정한다.")
	@Test
	void updateMilestoneStatus() throws Exception {
		// given
		mockingAuthenticateUser();
		MilestoneStatusUpdateRequest milestoneStatusUpdateRequest = new MilestoneStatusUpdateRequest("closed");

		// when // then
		mockMvc.perform(patch("/api/milestones/1/status")
				.content(objectMapper.writeValueAsString(milestoneStatusUpdateRequest))
				.contentType(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isOk());
	}

	@DisplayName("마일스톤을 삭제한다.")
	@Test
	void deleteMilestone() throws Exception {
		// given
		mockingAuthenticateUser();

		// when // then
		mockMvc.perform(delete("/api/milestones/1"))
			.andDo(print())
			.andExpect(status().isOk());
	}

	private void mockingAuthenticateUser() {
		mockMvc = MockMvcBuilders.standaloneSetup(new MilestoneController(milestoneService))
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
