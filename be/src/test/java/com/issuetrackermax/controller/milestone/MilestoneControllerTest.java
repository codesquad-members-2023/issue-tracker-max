package com.issuetrackermax.controller.milestone;

import static com.issuetrackermax.fixture.EntityFixture.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import com.issuetrackermax.controller.ControllerTestSupport;
import com.issuetrackermax.controller.label.dto.response.LabelDetailResponse;
import com.issuetrackermax.controller.milestone.dto.request.MilestonePostRequest;
import com.issuetrackermax.controller.milestone.dto.response.MilestoneDetailResponse;
import com.issuetrackermax.domain.milestone.entity.Milestone;

class MilestoneControllerTest extends ControllerTestSupport {

	@DisplayName("저장된 마일스톤을 반환한다.")
	@Test
	void show() throws Exception {
		// given
		Milestone milestone = makeMilestone(true, "title", "description");
		Milestone milestone2 = makeMilestone(true, "title2", "description2");

		MilestoneDetailResponse milestoneDetailResponse = MilestoneDetailResponse.builder()
			.milestone(milestone)
			.openIssueCount(1L)
			.closedIssueCount(2L)
			.build();

		MilestoneDetailResponse milestoneDetailResponse2 = MilestoneDetailResponse.builder()
			.milestone(milestone2)
			.openIssueCount(3L)
			.closedIssueCount(4L)
			.build();

		when(labelService.getLabelList()).thenReturn(List.of(
			LabelDetailResponse.builder().build(), LabelDetailResponse.builder().build()
		));
		when(milestoneService.getOpenMilestone()).thenReturn(
			List.of(milestoneDetailResponse, milestoneDetailResponse2));
		when(milestoneService.getClosedMilestone()).thenReturn(
			List.of(milestoneDetailResponse, milestoneDetailResponse2));

		// when & then
		mockMvc.perform(
				get("/api/milestones/open")
			)
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.success").value("true"))
			.andExpect(jsonPath("$.data.labelCount").value(2L))
			.andExpect(jsonPath("$.data.oppositeCount").value(2L))
			.andExpect(jsonPath("$.data.milestones[0].title").value("title"))
			.andExpect(jsonPath("$.data.milestones[0].description").value("description"))
			.andExpect(jsonPath("$.data.milestones[0].openIssueCount").value(1L))
			.andExpect(jsonPath("$.data.milestones[0].closedIssueCount").value(2L))
			.andExpect(jsonPath("$.data.milestones[1].title").value("title2"))
			.andExpect(jsonPath("$.data.milestones[1].description").value("description2"))
			.andExpect(jsonPath("$.data.milestones[1].openIssueCount").value(3L))
			.andExpect(jsonPath("$.data.milestones[1].closedIssueCount").value(4L));
	}

	@DisplayName("title, dueDate, description 입력하면 등록된다.")
	@Test
	void postLabel() throws Exception {

		// given
		MilestonePostRequest request = MilestonePostRequest.builder()
			.title("title")
			.description("description")
			.dueDate(LocalDateTime.now())
			.build();

		when(milestoneService.save(any())).thenReturn(1L);

		// when & then
		mockMvc.perform(
				post("/api/milestones")
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(request))
			)
			.andDo(print())
			.andExpect(jsonPath("$.success").value("true"))
			.andExpect(jsonPath("$.data.id").value(1L));
	}
}
