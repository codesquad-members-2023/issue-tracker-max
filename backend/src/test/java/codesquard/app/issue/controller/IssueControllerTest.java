package codesquard.app.issue.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import codesquard.app.ControllerTestSupport;
import codesquard.app.issue.dto.request.IssueSaveRequest;
import codesquard.app.issue.fixture.FixtureFactory;

class IssueControllerTest extends ControllerTestSupport {

	@DisplayName("이슈를 등록한다.")
	@Test
	void create() throws Exception {
		// given
		IssueSaveRequest issueSaveRequest = FixtureFactory.createIssueRegisterRequest("Controller", 1L);

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
		IssueSaveRequest zero = FixtureFactory.createIssueRegisterRequest(zeroTitle, 1L);

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
		String title51 = "123456789101112131415161718192021222324252627282930";

		IssueSaveRequest over50 = FixtureFactory.createIssueRegisterRequest(title51, 1L);

		// when & then
		mockMvc.perform(post("/api/issues")
				.content(objectMapper.writeValueAsString(over50))
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest())
			.andDo(print());
	}

	@DisplayName("제목이 공백이라면 400 에러를 반환한다.")
	@Test
	void create_InputBlankTitle_Response400() throws Exception {
		// given
		String blankTitle = " ";
		IssueSaveRequest blank = FixtureFactory.createIssueRegisterRequest(blankTitle, 1L);

		// when & then
		mockMvc.perform(post("/api/issues")
				.content(objectMapper.writeValueAsString(blank))
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest())
			.andDo(print());
	}
}
