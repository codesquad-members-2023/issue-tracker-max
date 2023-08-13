package codesquad.issueTracker.milestone.intergration;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;

import annotation.IntegrationTest;
import codesquad.issueTracker.jwt.domain.Jwt;
import codesquad.issueTracker.jwt.util.JwtProvider;
import codesquad.issueTracker.milestone.dto.MileStoneStatusDto;
import codesquad.issueTracker.milestone.dto.ModifyMilestoneRequestDto;

@IntegrationTest
public class MilestoneIntegrationTest {
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private JwtProvider jwtProvider;
	@Autowired
	private ObjectMapper objectMapper;

	private String jwtToken;

	@BeforeEach
	void setUp() {
		Jwt jwt = jwtProvider.createJwt(Map.of("memberId", 1L));
		jwtToken = jwt.getAccessToken();
	}

	@Test
	@DisplayName("마일스톤 저장 통합 테스트")
	void postMileStones() throws Exception {
		// given
		ModifyMilestoneRequestDto requestDto = new ModifyMilestoneRequestDto("이름입니다.", "설명입니다", "2023-09-22");
		String request = objectMapper.writeValueAsString(requestDto);
		// when
		ResultActions resultActions = mockMvc.perform(post("/api/milestones")
			.header("Authorization", "Bearer " + jwtToken)
			.contentType(MediaType.APPLICATION_JSON)
			.content(request));
		// then
		resultActions.andExpect(status().isOk())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.message", containsString("요청이 성공적으로 처리되었습니다.")));

	}

	@DisplayName("잘못된 날짜 형식 입력시 저장 실패 -> GlobalExceptionHandler 에서 처리하는 예외 검증")
	@Test
	void postMileStonesFailed() throws Exception {
		// given
		ModifyMilestoneRequestDto requestDto = new ModifyMilestoneRequestDto("이름입니다.", "설명입니다", "2023-09-35");
		String request = objectMapper.writeValueAsString(requestDto);
		// when
		ResultActions resultActions = mockMvc.perform(post("/api/milestones")
			.header("Authorization", "Bearer " + jwtToken)
			.contentType(MediaType.APPLICATION_JSON)
			.content(request));
		// then
		resultActions
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.message", containsString("유효하지 않은 날짜 입니다.")));
	}

	@Test
	@DisplayName("마일스톤 수정 통합 테스트 ")
	void patchMilestone() throws Exception {
		// given
		ModifyMilestoneRequestDto requestDto = new ModifyMilestoneRequestDto("수정마일스톤 .", "수정마일스톤입니다.", "2023-09-29");
		String request = objectMapper.writeValueAsString(requestDto);
		// when
		ResultActions resultActions = mockMvc.perform(patch("/api/milestones/1")
			.header("Authorization", "Bearer " + jwtToken)
			.contentType(MediaType.APPLICATION_JSON)
			.content(request));
		// then
		resultActions
			.andExpect(status().isOk())
			.andDo(print())
			.andExpect(jsonPath("$.message", containsString("요청이 성공적으로 처리되었습니다.")));
	}

	@Test
	@DisplayName("마일스톤 상태 변경 통합 테스트")
	void patchMilestoneStatus() throws Exception {
		// given
		MileStoneStatusDto requestDto = new MileStoneStatusDto("closed");
		String request = objectMapper.writeValueAsString(requestDto);
		// when
		ResultActions resultActions = mockMvc.perform(patch("/api/milestones/status/1")
			.header("Authorization", "Bearer " + jwtToken)
			.contentType(MediaType.APPLICATION_JSON)
			.content(request));
		// then
		resultActions
			.andExpect(status().isOk())
			.andDo(print())
			.andExpect(jsonPath("$.message", containsString("요청이 성공적으로 처리되었습니다.")));

	}

	@Test
	@DisplayName("마일스톤 삭제 통합 테스트")
	void deleteMilestone() throws Exception {
		// when
		ResultActions resultActions = mockMvc.perform(delete("/api/milestones/1")
			.header("Authorization", "Bearer " + jwtToken));
		// then
		resultActions
			.andExpect(status().isOk())
			.andDo(print())
			.andExpect(jsonPath("$.message", containsString("요청이 성공적으로 처리되었습니다.")));
	}

	@Test
	@DisplayName("상태별 마일스톤 조회 통합 테스트 case : open ")
	void getOpenMilestones() throws Exception {
		// given
		MileStoneStatusDto requestDto = new MileStoneStatusDto("open");
		String request = objectMapper.writeValueAsString(requestDto);
		// when
		ResultActions resultActions = mockMvc.perform(get("/api/milestones")
			.header("Authorization", "Bearer " + jwtToken)
			.contentType(MediaType.APPLICATION_JSON)
			.content(request));

		resultActions
			.andExpect(status().isOk())
			.andExpectAll(
				jsonPath("$.message.labelCount").value(3),
				jsonPath("$.message.anotherMilestoneCount").value(1),
				jsonPath("$.message.milestones.[0].name").value("sprint1"),
				jsonPath("$.message.milestones.[0].description").value("1번마일스톤"),
				jsonPath("$.message.milestones.[0].doneDate").value("2023-09-20"),
				jsonPath("$.message.milestones.[0].issueOpenCount").value(2),
				jsonPath("$.message.milestones.[0].issueClosedCount").value(1)
			);

	}

	@Test
	@DisplayName("상태별 마일스톤 조회 통합 테스트 case : closed ")
	void getClosedMilestones() throws Exception {
		// given
		MileStoneStatusDto requestDto = new MileStoneStatusDto("closed");
		String request = objectMapper.writeValueAsString(requestDto);
		// when
		ResultActions resultActions = mockMvc.perform(get("/api/milestones")
			.header("Authorization", "Bearer " + jwtToken)
			.contentType(MediaType.APPLICATION_JSON)
			.content(request));

		resultActions
			.andExpect(status().isOk())
			.andExpectAll(
				jsonPath("$.message.labelCount").value(3),
				jsonPath("$.message.anotherMilestoneCount").value(3),
				jsonPath("$.message.milestones.[0].name").value("sprint1"),
				jsonPath("$.message.milestones.[0].description").value("4번마일스톤"),
				jsonPath("$.message.milestones.[0].doneDate").value("2023-09-20"),
				jsonPath("$.message.milestones.[0].issueOpenCount").value(0),
				jsonPath("$.message.milestones.[0].issueClosedCount").value(0)
			);

	}

}
