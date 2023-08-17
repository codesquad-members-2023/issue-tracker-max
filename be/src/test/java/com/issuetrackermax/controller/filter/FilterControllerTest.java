package com.issuetrackermax.controller.filter;

import static com.issuetrackermax.fixture.EntityFixture.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.issuetrackermax.controller.ControllerTestSupport;
import com.issuetrackermax.controller.filter.dto.response.FilterResponse;
import com.issuetrackermax.controller.filter.dto.response.IssueResponse;
import com.issuetrackermax.controller.filter.dto.response.LabelResponse;
import com.issuetrackermax.domain.filter.FilterResultVO;

class FilterControllerTest extends ControllerTestSupport {

	@DisplayName("필터가 없으면 열린 이슈를 담은 메인 페이지를 반환한다.")
	@Test
	void mainPageWithNoFilter() throws Exception {

		// given

		// mock the returned value of request.getParameterMap()
		LocalDateTime localDateTime = LocalDateTime.now();
		FilterResultVO filterResultVO = FilterResultVO.builder()
			.id(1L)
			.isOpen(true)
			.editor("June")
			.title("issue_title")
			.labelIds("1,2")
			.writerId(1L)
			.writer("June")
			.writerImageUrl("https://issue-tracker-bucket-04.s3.ap-northeast-2.amazonaws.com/profile/green.jpg")
			.assigneeIds("1,2")
			.assigneeNames("June,Movie")
			.milestoneId(1L)
			.milestoneTitle("milestone")
			.modifiedAt(localDateTime)
			.build();
		List<LabelResponse> labelResponses = List.of(
			LabelResponse.from(makeLabel("label_title", "description", "0#1111", "0#2222")),
			LabelResponse.from(makeLabel("label_title2", "description", "0#1111", "0#2222"))
		);
		IssueResponse issueResponse = IssueResponse.builder().resultVO(filterResultVO).labels(labelResponses).build();

		when(filterService.getMainPageIssue(any())).thenReturn(
			FilterResponse.builder()
				.labelCount(1L)
				.milestoneCount(1L)
				.closedIssueCount(1L)
				.openIssueCount(1L)
				.issues(
					List.of(
						issueResponse
					))
				.build()
		);

		// when&then
		mockMvc.perform(
				get("/api/").requestAttr("memberId", 1)
			)
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.success").value("true"))
			.andExpect(jsonPath("$.data.labelCount").value(1L))
			.andExpect(jsonPath("$.data.milestoneCount").value(1L))
			.andExpect(jsonPath("$.data.openIssueCount").value(1L))
			.andExpect(jsonPath("$.data.closedIssueCount").value(1L))
			.andExpect(jsonPath("$.data.issues[0].id").value(1L))
			.andExpect(jsonPath("$.data.issues[0].isOpen").value(true))
			.andExpect(jsonPath("$.data.issues[0].title").value("issue_title"))
			.andExpect(jsonPath("$.data.issues[0].history.editor").value("June"))
			.andExpect(jsonPath("$.data.issues[0].labels[0].title").value("label_title"))
			.andExpect(jsonPath("$.data.issues[0].labels[0].textColor").value("0#1111"))
			.andExpect(jsonPath("$.data.issues[0].labels[0].backgroundColor").value("0#2222"))
			.andExpect(jsonPath("$.data.issues[0].labels[1].title").value("label_title2"))
			.andExpect(jsonPath("$.data.issues[0].labels[1].textColor").value("0#1111"))
			.andExpect(jsonPath("$.data.issues[0].labels[1].backgroundColor").value("0#2222"))
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
		when(filterService.getMainPageIssue(any())).thenReturn(
			FilterResponse.builder()
				.labelCount(1L)
				.milestoneCount(1L)
				.openIssueCount(1L)
				.closedIssueCount(1L)
				.issues(null)
				.build());

		// when & then
		mockMvc.perform(
				get("/api/").requestAttr("memberId", 1)
			)
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.success").value("true"))
			.andExpect(jsonPath("$.data.labelCount").value(1L))
			.andExpect(jsonPath("$.data.milestoneCount").value(1L))
			.andExpect(jsonPath("$.data.openIssueCount").value(1L))
			.andExpect(jsonPath("$.data.closedIssueCount").value(1L))
			.andExpect(jsonPath("$.data.issues").isEmpty());
	}
}
