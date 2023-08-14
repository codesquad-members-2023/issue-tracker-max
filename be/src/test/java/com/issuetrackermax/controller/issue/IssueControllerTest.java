package com.issuetrackermax.controller.issue;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import com.issuetrackermax.common.exception.ApiException;
import com.issuetrackermax.common.exception.domain.IssueException;
import com.issuetrackermax.controller.ControllerTestSupport;
import com.issuetrackermax.controller.issue.dto.request.IssuePostRequest;
import com.issuetrackermax.controller.issue.dto.request.IssueTitleRequest;
import com.issuetrackermax.controller.issue.dto.request.IssuesStatusRequest;
import com.issuetrackermax.controller.issue.dto.response.IssueDetailsResponse;
import com.issuetrackermax.controller.issue.dto.response.IssuePostResponse;
import com.issuetrackermax.domain.history.entity.History;
import com.issuetrackermax.domain.issue.entity.IssueResultVO;

public class IssueControllerTest extends ControllerTestSupport {

	@DisplayName("새로운 이슈를 생성한다.")
	@Test
	void postIssue() throws Exception {
		//given
		IssuePostRequest request = IssuePostRequest.builder()
			.title("title")
			.content("content")
			.build();

		IssuePostResponse issuePostResponse = new IssuePostResponse(1L);
		when(issueService.post(any(), anyLong())).thenReturn(issuePostResponse);

		//when & then
		mockMvc.perform(
				post("/api/issues")
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(request))
					.requestAttr("memberId", 1)
			)
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.success").value(true))
			.andExpect(jsonPath("$.data.id").value(1L));
	}

	@DisplayName("이슈 생성 시 이슈 제목이 공백이면 예외를 발생한다.")
	@Test
	void postAndThrowException() throws Exception {
		//given
		IssuePostRequest request = IssuePostRequest.builder()
			.title("")
			.content("content")
			.build();

		//when & then
		mockMvc.perform(
				post("/api/issues")
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(request))
					.requestAttr("memberId", 1)
			)
			.andDo(print())
			.andExpect(jsonPath("$.success").value(false))
			.andExpect(jsonPath("$.errorCode.status").value(400))
			.andExpect(jsonPath("$.errorCode.message").value("이슈 제목을 입력해주세요."));
	}

	@DisplayName("특정 이슈 상세 정보를 가져온다.")
	@Test
	void showIssue() throws Exception {
		//given
		Long issueId = 1L;
		IssueResultVO resultVO = IssueResultVO.builder()
			.id(issueId)
			.isOpen(true)
			.title("title")
			.labelIds("1,2")
			.writerId(1L)
			.writer("june")
			.assigneeIds("2,3")
			.assigneeNames("movie, toko")
			.milestoneId(1L)
			.milestoneTitle("milestone")
			.build();

		History history = History.builder()
			.id(1L)
			.editor("june")
			.issueId(issueId)
			.issueIsOpen(true)
			.modifiedAt(LocalDateTime.now())
			.build();

		IssueDetailsResponse response = IssueDetailsResponse.builder()
			.resultVO(resultVO)
			.history(history)
			.comments(null)
			.build();
		when(issueService.show(anyLong())).thenReturn(response);

		//when & then
		mockMvc.perform(
				get("/api/issues/" + issueId))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.success").value(true));
	}

	@DisplayName("잘못된 이슈 조회 시 예외를 발생한다.")
	@Test
	void showIssueAndThrowException() throws Exception {
		//given
		long invalidIssueId = 7000L;

		when(issueService.show(anyLong())).thenThrow(new ApiException(IssueException.NOT_FOUND_ISSUE));

		//when & then
		mockMvc.perform(
				get("/api/issues/" + invalidIssueId))
			.andDo(print())
			.andExpect(jsonPath("$.success").value(false))
			.andExpect(jsonPath("$.errorCode.status").value(404))
			.andExpect(jsonPath("$.errorCode.message").value("존재하지 않는 이슈입니다."));
	}

	@DisplayName("선택한 이슈의 상태를 open으로 변경한다.")
	@Test
	void openIssue() throws Exception {
		//given
		List<Long> ids = Arrays.asList(1L, 2L, 3L);

		IssuesStatusRequest request = IssuesStatusRequest
			.builder()
			.issueIds(ids)
			.issueStatus("open")
			.build();

		//when & then
		mockMvc.perform(
				patch("/api/issues/status")
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(request))
					.requestAttr("memberId", 1)
			)
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.success").value(true));
	}

	@DisplayName("선택한 이슈의 상태를 close로 변경한다.")
	@Test
	void closeIssue() throws Exception {
		//given
		List<Long> ids = Arrays.asList(1L, 2L, 3L);

		IssuesStatusRequest request = IssuesStatusRequest
			.builder()
			.issueIds(ids)
			.issueStatus("close")
			.build();

		//when & then
		mockMvc.perform(
				patch("/api/issues/status")
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(request))
					.requestAttr("memberId", 1)
			)
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.success").value(true));
	}

	@DisplayName("정해진 상태 외 다른 상태로 변경 시 예외를 발생한다.")
	@Test
	void updateStatusAndThrowException() throws Exception {
		//given
		List<Long> ids = Arrays.asList(1L, 2L, 3L);

		IssuesStatusRequest request = IssuesStatusRequest
			.builder()
			.issueIds(ids)
			.issueStatus("in progress")
			.build();

		doThrow(new ApiException(IssueException.INVALID_ISSUE_STATUS)).when(issueService)
			.updateStatus(any(), anyLong());

		//when & then
		mockMvc.perform(
				patch("/api/issues/status")
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(request))
					.requestAttr("memberId", 1)
			)
			.andDo(print())
			.andExpect(jsonPath("$.success").value(false))
			.andExpect(jsonPath("$.errorCode.status").value(400))
			.andExpect(jsonPath("$.errorCode.message").value("유효하지 않은 요청입니다."));
	}

	@DisplayName("기존 이슈의 제목을 변경한다.")
	@Test
	void modifyTitle() throws Exception {
		//given
		long issueId = 1L;
		IssueTitleRequest request = IssueTitleRequest.builder()
			.title("수정된 제목")
			.build();

		//when & then
		mockMvc.perform(
				patch("/api/issues/" + issueId + "/title")
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(request))
			)
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.success").value(true));
	}

	@DisplayName("변경하려는 제목이 공백이면 예외를 발생한다.")
	@Test
	void modifyTitleAneThrowException() throws Exception {
		//given
		long issueId = 1L;
		IssueTitleRequest request = IssueTitleRequest.builder()
			.title("")
			.build();

		//when & then
		mockMvc.perform(
				patch("/api/issues/" + issueId + "/title")
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(request))
			)
			.andDo(print())
			.andExpect(jsonPath("$.success").value(false))
			.andExpect(jsonPath("$.errorCode.status").value(400))
			.andExpect(jsonPath("$.errorCode.message").value("이슈 제목을 입력해주세요."));
	}
}
