package codesquard.app.milestone.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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
