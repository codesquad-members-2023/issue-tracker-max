package codesquard.app.issue.controller;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import codesquard.app.ControllerTestSupport;
import codesquard.app.errors.errorcode.IssueErrorCode;
import codesquard.app.errors.exception.IllegalIssueStatusException;
import codesquard.app.issue.dto.request.IssueModifyAssigneesRequest;
import codesquard.app.issue.dto.request.IssueModifyContentRequest;
import codesquard.app.issue.dto.request.IssueModifyLabelsRequest;
import codesquard.app.issue.dto.request.IssueModifyMilestoneRequest;
import codesquard.app.issue.dto.request.IssueModifyStatusRequest;
import codesquard.app.issue.dto.request.IssueModifyTitleRequest;
import codesquard.app.issue.dto.request.IssueSaveRequest;
import codesquard.app.issue.fixture.FixtureFactory;

class IssueControllerTest extends ControllerTestSupport {

	@DisplayName("이슈의 상세 내용을 조회한다.")
	@Test
	void getIssueDetail() throws Exception {
		// given
		int id = 1;
		given(issueService.get((long)id)).willReturn(FixtureFactory.createIssueReadResponse((long)id));

		// when & then
		mockMvc.perform(get("/api/issues/" + id))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.title").exists())
			.andExpect(jsonPath("$.content").exists())
			.andDo(print());
	}

	@DisplayName("이슈를 등록한다.")
	@Test
	void create() throws Exception {
		// given
		IssueSaveRequest issueSaveRequest = FixtureFactory.createIssueRegisterRequest("Controller", "내용", 1L);

		// when & then
		mockMvc.perform(post("/api/issues")
				.content(objectMapper.writeValueAsString(issueSaveRequest))
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.success").value(true))
			.andDo(print());
	}

	@DisplayName("제목이 1글자 미만이라면 400 에러를 반환한다.")
	@Test
	void create_InputZeroTitle_Response400() throws Exception {
		// given
		String zeroTitle = "";
		IssueSaveRequest zero = FixtureFactory.createIssueRegisterRequest(zeroTitle, "내용", 1L);

		// when & then
		mockMvc.perform(post("/api/issues")
				.content(objectMapper.writeValueAsString(zero))
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest())
			.andDo(print());
	}

	@DisplayName("제목이 50글자 초과라면 400 에러를 반환한다.")
	@Test
	void create_Input51Title_Response400() throws Exception {
		// given
		String title = "123456789101112131415161718192021222324252627282930";

		IssueSaveRequest over = FixtureFactory.createIssueRegisterRequest(title, "내용", 1L);

		// when & then
		mockMvc.perform(post("/api/issues")
				.content(objectMapper.writeValueAsString(over))
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest())
			.andDo(print());
	}

	@DisplayName("제목이 공백이라면 400 에러를 반환한다.")
	@Test
	void create_InputBlankTitle_Response400() throws Exception {
		// given
		String blankTitle = " ";
		IssueSaveRequest blank = FixtureFactory.createIssueRegisterRequest(blankTitle, "내용", 1L);

		// when & then
		mockMvc.perform(post("/api/issues")
				.content(objectMapper.writeValueAsString(blank))
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest())
			.andDo(print());
	}

	@DisplayName("내용이 10000글자 초과라면 400 에러를 반환한다.")
	@Test
	void create_Input10000Content_Response400() throws Exception {
		// given
		String content = generateExceedingMaxLengthContent(10000);

		IssueSaveRequest over = FixtureFactory.createIssueRegisterRequest("Controller", content, 1L);

		// when & then
		mockMvc.perform(post("/api/issues")
				.content(objectMapper.writeValueAsString(over))
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest())
			.andDo(print());
	}

	@DisplayName("이슈 상태를 수정한다.")
	@Test
	void modifyStatus() throws Exception {
		// given
		int issueId = 1;
		IssueModifyStatusRequest issueModifyStatusRequest = new IssueModifyStatusRequest("CLOSED");

		// when & then
		mockMvc.perform(patch("/api/issues/" + issueId + "/status")
				.content(objectMapper.writeValueAsString(issueModifyStatusRequest))
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.success").value(true))
			.andDo(print());
	}

	@DisplayName("이슈 상태 수정시 유효하지 않은 status 값이 오면 400 에러를 반환한다.")
	@Test
	void modifyInvalidStatus_Response400() throws Exception {
		// given
		int issueId = 1;
		IssueModifyStatusRequest issueModifyStatusRequest = new IssueModifyStatusRequest("OPEN");
		willThrow(new IllegalIssueStatusException(IssueErrorCode.INVALID_ISSUE_STATUS))
			.given(issueService).modifyStatus(any(IssueModifyStatusRequest.class), anyLong());

		// when & then
		mockMvc.perform(patch("/api/issues/" + issueId + "/status")
				.content(objectMapper.writeValueAsString(issueModifyStatusRequest))
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest())
			.andDo(print());
	}

	@DisplayName("이슈 제목을 수정한다.")
	@Test
	void modifyTitle() throws Exception {
		// given
		int issueId = 1;
		IssueModifyTitleRequest issueModifyTitleRequest = new IssueModifyTitleRequest("수정된 제목");

		// when & then
		mockMvc.perform(patch("/api/issues/" + issueId + "/title")
				.content(objectMapper.writeValueAsString(issueModifyTitleRequest))
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.success").value(true))
			.andDo(print());
	}

	@DisplayName("이슈 제목 수정시 제목이 1글자 미만이라면 400 에러를 반환한다.")
	@Test
	void modify_InputZeroTitle_Response400() throws Exception {
		// given
		int issueId = 1;
		String zeroTitle = "";
		IssueModifyTitleRequest issueModifyTitleRequest = new IssueModifyTitleRequest(zeroTitle);

		// when & then
		mockMvc.perform(patch("/api/issues/" + issueId + "/title")
				.content(objectMapper.writeValueAsString(issueModifyTitleRequest))
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest())
			.andDo(print());
	}

	@DisplayName("이슈 제목 수정시 제목이 50글자 초과라면 400 에러를 반환한다.")
	@Test
	void modify_Input51Title_Response400() throws Exception {
		// given
		int issueId = 1;
		String title = generateExceedingMaxLengthContent(51);
		IssueModifyTitleRequest issueModifyTitleRequest = new IssueModifyTitleRequest(title);

		// when & then
		mockMvc.perform(patch("/api/issues/" + issueId + "/title")
				.content(objectMapper.writeValueAsString(issueModifyTitleRequest))
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest())
			.andDo(print());
	}

	@DisplayName("이슈 제목 수정시 제목이 공백이라면 400 에러를 반환한다.")
	@Test
	void modify_InputBlankTitle_Response400() throws Exception {
		// given
		int issueId = 1;
		String blankTitle = " ";
		IssueModifyTitleRequest issueModifyTitleRequest = new IssueModifyTitleRequest(blankTitle);

		// when & then
		mockMvc.perform(patch("/api/issues/" + issueId + "/title")
				.content(objectMapper.writeValueAsString(issueModifyTitleRequest))
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest())
			.andDo(print());
	}

	@DisplayName("이슈 내용을 수정한다.")
	@Test
	void modifyContent() throws Exception {
		// given
		int issueId = 1;
		IssueModifyContentRequest issueModifyContentRequest = new IssueModifyContentRequest("수정된 내용");

		// when & then
		mockMvc.perform(patch("/api/issues/" + issueId + "/content")
				.content(objectMapper.writeValueAsString(issueModifyContentRequest))
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.success").value(true))
			.andDo(print());
	}

	@DisplayName("이슈 내용 수정시 내용이 10000글자 초과라면 400 에러를 반환한다.")
	@Test
	void modify_Input10000Content_Response400() throws Exception {
		// given
		int issueId = 1;
		String title = generateExceedingMaxLengthContent(10000);
		IssueModifyContentRequest issueModifyContentRequest = new IssueModifyContentRequest(title);

		// when & then
		mockMvc.perform(patch("/api/issues/" + issueId + "/content")
				.content(objectMapper.writeValueAsString(issueModifyContentRequest))
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest())
			.andDo(print());
	}

	@DisplayName("이슈 마일스톤을 수정한다.")
	@Test
	void modifyMilestone() throws Exception {
		// given
		int issueId = 1;
		IssueModifyMilestoneRequest issueModifyMilestoneRequest = new IssueModifyMilestoneRequest(2L);

		// when & then
		mockMvc.perform(patch("/api/issues/" + issueId + "/milestones")
				.content(objectMapper.writeValueAsString(issueModifyMilestoneRequest))
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.success").value(true))
			.andDo(print());
	}

	@DisplayName("이슈 책임자를 수정한다.")
	@Test
	void modifyAssignees() throws Exception {
		// given
		int issueId = 1;
		IssueModifyAssigneesRequest issueModifyAssigneesRequest = new IssueModifyAssigneesRequest(List.of(1L, 2L));

		// when & then
		mockMvc.perform(patch("/api/issues/" + issueId + "/assignees")
				.content(objectMapper.writeValueAsString(issueModifyAssigneesRequest))
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.success").value(true))
			.andDo(print());
	}

	@DisplayName("이슈 라벨을 수정한다.")
	@Test
	void modifyLabels() throws Exception {
		// given
		int issueId = 1;
		IssueModifyLabelsRequest issueModifyLabelsRequest = new IssueModifyLabelsRequest(List.of(1L, 2L));

		// when & then
		mockMvc.perform(patch("/api/issues/" + issueId + "/labels")
				.content(objectMapper.writeValueAsString(issueModifyLabelsRequest))
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.success").value(true))
			.andDo(print());
	}

	@DisplayName("이슈를 삭제한다.")
	@Test
	void deleteIssue() throws Exception {
		// given
		int issueId = 1;

		// when & then
		mockMvc.perform(delete("/api/issues/" + issueId))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.success").value(true))
			.andDo(print());
	}

	private String generateExceedingMaxLengthContent(int maxLength) {
		StringBuilder builder = new StringBuilder();
		while (builder.length() < maxLength) {
			builder.append("Talk is cheap. Show me the code. ");
		}
		return builder.toString();
	}
}
