package codesquad.issueTracker.milestone.controller;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import annotation.ControllerTest;
import codesquad.issueTracker.milestone.dto.ModifyMilestoneRequestDto;
import codesquad.issueTracker.milestone.service.MilestoneService;

@ControllerTest(MilestoneController.class)
class MilestoneControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private MilestoneService milestoneService;

	@Test
	@DisplayName("마일 스톤 등록,수정시 이름,날짜를 올바르게 입력했을때 성공 ")
	void requestValidSuccess() throws Exception {
		ModifyMilestoneRequestDto requestDto = new ModifyMilestoneRequestDto("이름입력했습니다.", "ddd", "2024-01-01");
		String request = objectMapper.writeValueAsString(requestDto);
		mockMvc.perform(post("/api/milestones")
				.contentType(MediaType.APPLICATION_JSON)
				.content(request))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.message", containsString("요청이 성공적으로 처리되었습니다.")));
	}

	@Test
	@DisplayName("마일 스톤 등록,수정시 이름을 입력하지 않았을 때 응답 : BadRequest , 에러메시지 : 이름을 필수로 입력해 주세요 출력")
	void requestNameValidFailed() throws Exception {
		ModifyMilestoneRequestDto requestDto = new ModifyMilestoneRequestDto(null, "ddd", "2024-01-01");
		String request = objectMapper.writeValueAsString(requestDto);
		mockMvc.perform(post("/api/milestones")
				.contentType(MediaType.APPLICATION_JSON)
				.content(request))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.message", containsString("이름을 필수로 입력해주세요")));
	}

	@Test
	@DisplayName("마일 스톤 등록,수정시 날짜를 yyyy-mm-dd 형식으로 입력하지  않았을 때 응답 : BadRequest , 에러메시지 : yyyy-mm-dd 형식으로 입력해주세요 ")
	void requestDateValidFailed() throws Exception {
		ModifyMilestoneRequestDto requestDto = new ModifyMilestoneRequestDto("이름입력했어용", "ddd", "20224-01-01");
		String request = objectMapper.writeValueAsString(requestDto);

		mockMvc.perform(post("/api/milestones")
				.contentType(MediaType.APPLICATION_JSON)
				.content(request))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.message", containsString("yyyy-mm-dd 형식으로 입력해주세요")));
	}

}