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
import org.presents.issuetracker.issue.dto.request.IssueStatusUpdateRequest;
import org.presents.issuetracker.issue.dto.request.IssueUpdateRequest;
import org.presents.issuetracker.issue.dto.response.IssueDetailResponse;
import org.presents.issuetracker.issue.service.IssueService;
import org.presents.issuetracker.label.dto.response.LabelPreviewResponse;
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
			.andExpect(jsonPath("$.id").value(7))
			.andDo(print());
	}

	@Test
	@DisplayName("수정할 이슈의 제목과 내용을 입력받아 이슈를 수정하고 수정한 이슈 아이디를 반환한다.")
	public void updateDetail() throws Exception {
		//given
		IssueUpdateRequest issueUpdateRequest = new IssueUpdateRequest(1L, "제목 수정", "내용 수정");
		given(issueService.updateTitle(BDDMockito.any())).willReturn(1L);

		//when then
		mockMvc.perform(patch("/issues/title")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(issueUpdateRequest)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id").value(1))
			.andDo(print());
	}

	@Test
	@DisplayName("레이블 아이디 리스트를 입력받아 이슈의 레이블을 수정하고 이슈의 레이블 목록을 반환한다.")
	public void updateLabels() throws Exception {
		//given
		List<Long> labelIds = List.of(1L, 2L);
		List<LabelPreviewResponse> labels = List.of(LabelPreviewResponse.builder().id(1L).name("레이블1").build(),
			LabelPreviewResponse.builder().id(2L).name("레이블2").build());
		given(issueService.updateLabels(BDDMockito.any(), BDDMockito.anyLong())).willReturn(labels);

		//when then
		mockMvc.perform(put("/issues/1/labels")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(labelIds)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$[0].id").value(1))
			.andExpect(jsonPath("$[0].name").value("레이블1"))
			.andExpect(jsonPath("$[1].id").value(2))
			.andExpect(jsonPath("$[1].name").value("레이블2"))
			.andDo(print());
	}

	@Test
	@DisplayName("담당자 아이디 리스트를 입력받아 이슈의 담당자를 수정하고 이슈의 담당자 목록을 반환한다.")
	public void updateAssignees() throws Exception {
		//given
		List<Long> assigneeIds = List.of(1L, 2L);
		List<UserResponse> userResponses = List.of(UserResponse.builder().userId(1L).loginId("id1").build(),
			UserResponse.builder().userId(2L).loginId("id2").build());
		given(issueService.updateAssignees(BDDMockito.any(), BDDMockito.anyLong())).willReturn(userResponses);

		//when then
		mockMvc.perform(put("/issues/1/assignees")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(assigneeIds)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$[0].userId").value(1))
			.andExpect(jsonPath("$[0].loginId").value("id1"))
			.andExpect(jsonPath("$[1].userId").value(2))
			.andExpect(jsonPath("$[1].loginId").value("id2"))
			.andDo(print());
	}

	@Test
	@DisplayName("마일스톤 아이디를 입력받아 이슈의 마일스톤을 수정하고 수정한 마일스톤을 반환한다.")
	public void updateMilestone() throws Exception {
		//given
		Long issueId = 1L;
		Long milestoneId = 2L;
		MilestonePreviewResponse milestone = MilestonePreviewResponse.builder().id(2L).name("마일스톤").progress(0).build();
		given(issueService.updateMilestone(BDDMockito.anyLong(), BDDMockito.anyLong())).willReturn(milestone);

		//when then
		mockMvc.perform(put("/issues/1/milestone")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(milestoneId)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id").value(2))
			.andExpect(jsonPath("$.name").value("마일스톤"))
			.andExpect(jsonPath("$.progress").value(0))
			.andDo(print());
	}

	@Test
	@DisplayName("변경할 이슈 아이디 목록과 상태를 입력받아 해당 이슈들의 상태를 변경한다.")
	public void updateStatus() throws Exception {
		//given
		List<Long> issueIds = List.of(1L, 2L);
		String close = "closed";
		IssueStatusUpdateRequest issueStatusUpdateRequest = new IssueStatusUpdateRequest(issueIds, close);
		doNothing().when(issueService).updateStatus(BDDMockito.any(), BDDMockito.anyString());

		//when then
		mockMvc.perform(put("/issues/status")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(issueStatusUpdateRequest)))
			.andExpect(status().isNoContent())
			.andDo(print());
	}

	@Test
	@DisplayName("이슈 아이디에 해당하는 이슈를 삭제한다.")
	public void deleteIssue() throws Exception {
		//given
		Long issueId = 1L;
		doNothing().when(issueService).delete(issueId);

		//when then
		mockMvc.perform(delete("/issues/1"))
			.andExpect(status().isNoContent())
			.andDo(print());
	}
}
