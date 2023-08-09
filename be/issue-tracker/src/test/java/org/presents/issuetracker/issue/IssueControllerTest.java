package org.presents.issuetracker.issue;

import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.presents.issuetracker.annotation.ControllerTest;
import org.presents.issuetracker.issue.controller.IssueController;
import org.presents.issuetracker.issue.dto.request.IssueCreateRequest;
import org.presents.issuetracker.issue.dto.response.IssueDetailResponse;
import org.presents.issuetracker.issue.service.IssueService;
import org.presents.issuetracker.milestone.dto.response.MilestonePreviewResponse;
import org.presents.issuetracker.user.dto.response.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

@ControllerTest(IssueController.class)
public class IssueControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private IssueService issueService;

	@Test
	@DisplayName("요청에 입력 받은 issueId로 이슈 상세 정보를 조회하고 IssueDetailResponse를 json으로 반환한다.")
	public void getIssueDetailTest() throws Exception {
		//given
		IssueDetailResponse issueDetailResponse = IssueDetailResponse.builder()
			.id(1L)
			.title("이슈1")
			.contents("이슈내용")
			.status("open")
			.author(UserResponse.builder()
				.userId(1L).loginId("user1").image("img1").build())
			.milestone(MilestonePreviewResponse.builder()
				.id(1L).name("마일스톤").progress(0).build())
			.assignees(List.of(
				UserResponse.builder().userId(1L).loginId("user1").image("img1").build(),
				UserResponse.builder().userId(2L).loginId("user2").image("img2").build()
			))
			.build();
		given(issueService.getIssueDetail(1L)).willReturn(issueDetailResponse);

		//when then
		mockMvc.perform(get("/issues/1"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id", is(1)))
			.andExpect(jsonPath("$.title", is("이슈1")))
			.andExpect(jsonPath("$.contents", is("이슈내용")))
			.andExpect(jsonPath("$.status", is("open")))
			.andExpect(jsonPath("$.author.userId", is(1)))
			.andExpect(jsonPath("$.author.loginId", is("user1")))
			.andExpect(jsonPath("$.author.image", is("img1")))
			.andExpect(jsonPath("$.milestone.id", is(1)))
			.andExpect(jsonPath("$.milestone.name", is("마일스톤")))
			.andExpect(jsonPath("$.milestone.progress", is(0)))
			.andExpect(jsonPath("$.assignees", hasSize(2)))
			.andDo(print());
	}

	@Test
	@DisplayName("새로운 이슈를 생성하고 생성된 이슈 아이디를 반환한다.")
	public void create() throws Exception {
		//given
		IssueCreateRequest issueCreateRequest = new IssueCreateRequest("제목1", "내용1", 1L, null, null, null);
		given(issueService.create(BDDMockito.any())).willReturn(7L);

		//when then
		mockMvc.perform(post("/issues/new")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(issueCreateRequest)))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.id", is(7)))
			.andDo(print());
	}
}
