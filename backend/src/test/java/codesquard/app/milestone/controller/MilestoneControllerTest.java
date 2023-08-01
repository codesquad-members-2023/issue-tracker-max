package codesquard.app.milestone.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import codesquard.app.ControllerTestSupport;
import codesquard.app.milestone.dto.request.MilestoneRequest;

class MilestoneControllerTest extends ControllerTestSupport {

	@DisplayName("마일스톤을 등록한다.")
	@Test
	void save() throws Exception {
		// given
		MilestoneRequest milestoneRequest = new MilestoneRequest("제목", LocalDate.now(), "내용");

		// when // then
		mockMvc.perform(post("/api/milestones")
				.content(objectMapper.writeValueAsString(milestoneRequest))
				.contentType(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.success").value(true));
	}
}
