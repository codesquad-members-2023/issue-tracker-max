package codesquard.app.milestone.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import codesquard.app.api.response.MilestoneResponseMessage;
import codesquard.app.label_milestone.ControllerTestSupport;
import codesquard.app.milestone.dto.request.MilestoneSaveRequest;

class MilestoneControllerTest extends ControllerTestSupport {

	@DisplayName("마일스톤을 등록한다.")
	@Test
	void save() throws Exception {
		// given
		MilestoneSaveRequest milestoneSaveRequest = new MilestoneSaveRequest("제목", LocalDate.now(), "내용");

		// when // then
		mockMvc.perform(post("/api/milestones")
				.content(objectMapper.writeValueAsString(milestoneSaveRequest))
				.contentType(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.code").value("201"))
			.andExpect(jsonPath("$.status").value("CREATED"))
			.andExpect(jsonPath("$.message").value(MilestoneResponseMessage.MILESTONE_SAVE_SUCCESS))
			.andExpect(jsonPath("$.data.id").isNotEmpty());
	}
}
