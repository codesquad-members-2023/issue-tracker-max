package com.issuetrackermax.controller.filter;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.issuetrackermax.controller.ControllerTestSupport;
import com.issuetrackermax.domain.filter.FilterResultVO;

class FilterControllerTest extends ControllerTestSupport {

	@DisplayName("필터가 없으면 열린 이슈를 담은 메인 페이지를 반환한다.")
	@Test
	void mainpageWithNoFilter() throws Exception {
		// given
		LocalDateTime localDateTime = LocalDateTime.now();
		when(filterMapper.getFilteredList(anyMap())).thenReturn(List.of(
			FilterResultVO.builder()
				.id(1L)
				.isOpen(true)
				.editor("June")
				.title("issue_title")
				.labelIds("1,2")
				.labelTitles("label_title,label_title2")
				.writerId(1L)
				.writer("June")
				.assigneeIds("1,2")
				.assigneeNames("June,Movie")
				.milestoneId(1L)
				.milestoneTitle("milestone")
				.modifiedAt(localDateTime)
				.build()));
		when(filterService.getLabelCount()).thenReturn(1L);
		when(filterService.getMilestoneCount()).thenReturn(1L);
		when(filterService.getClosedIssueCount()).thenReturn(1L);
		when(filterService.getOpenIssueCount()).thenReturn(1L);

		// when&then
		mockMvc.perform(
				get("/")
			)
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.success").value("true"))
			.andExpect(jsonPath("$.data.labelCount").value(1L))
			.andExpect(jsonPath("$.data.mileStoneCount").value(1L))
			.andExpect(jsonPath("$.data.openIssueCount").value(1L))
			.andExpect(jsonPath("$.data.closedIssueCount").value(1L))
			.andExpect(jsonPath("$.data.issues[0].id").value(1L))
			.andExpect(jsonPath("$.data.issues[0].isOpen").value(true))
			.andExpect(jsonPath("$.data.issues[0].title").value("issue_title"))
			.andExpect(jsonPath("$.data.issues[0].history.editor").value("June"))
			.andExpect(jsonPath("$.data.issues[0].history.modifiedAt").value(localDateTime.toString()))
			.andExpect(jsonPath("$.data.issues[0].labels[0].id").value(1L))
			.andExpect(jsonPath("$.data.issues[0].labels[0].title").value("label_title"))
			.andExpect(jsonPath("$.data.issues[0].labels[1].id").value(2L))
			.andExpect(jsonPath("$.data.issues[0].labels[1].title").value("label_title2"))
			.andExpect(jsonPath("$.data.issues[0].assignees[0].id").value(1L))
			.andExpect(jsonPath("$.data.issues[0].assignees[0].name").value("June"))
			.andExpect(jsonPath("$.data.issues[0].assignees[1].id").value(2L))
			.andExpect(jsonPath("$.data.issues[0].assignees[1].name").value("Movie"))
			.andExpect(jsonPath("$.data.issues[0].writer.id").value(1L))
			.andExpect(jsonPath("$.data.issues[0].writer.name").value("June"))
			.andExpect(jsonPath("$.data.issues[0].milestone.id").value(1L))
			.andExpect(jsonPath("$.data.issues[0].milestone.title").value("milestone"));

	}

	@DisplayName("필터에 해당하는 이슈가 없으면 빈 메인 페이지를 반환한다.")
	@Test
	void mainpageWithNoIssue() throws Exception {
		// given
		when(filterMapper.getFilteredList(anyMap())).thenReturn(List.of());

		// when & then
		mockMvc.perform(
				get("/")
			)
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.success").value("true"))
			.andExpect(jsonPath("$.data").isEmpty());
	}
}