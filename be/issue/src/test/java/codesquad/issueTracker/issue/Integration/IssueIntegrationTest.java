package codesquad.issueTracker.issue.Integration;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
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
import codesquad.issueTracker.issue.dto.IssueWriteRequestDto;
import codesquad.issueTracker.jwt.domain.Jwt;
import codesquad.issueTracker.jwt.util.JwtProvider;

@IntegrationTest
public class IssueIntegrationTest {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private JwtProvider jwtProvider;
	@Autowired
	private ObjectMapper objectMapper;

	private String jwtToken;

	@BeforeEach
	void setUp() {
		Jwt jwt = jwtProvider.createJwt(Map.of("userId", 1L));
		jwtToken = jwt.getAccessToken();
	}

	@Test
	@DisplayName("이슈 작성 통합테스트 ")
	void postIssues() throws Exception {
		// given
		List<Long> ids = List.of(1L, 2L);
		IssueWriteRequestDto requestDto = new IssueWriteRequestDto("제목", "내용", ids, ids, 1L);
		String request = objectMapper.writeValueAsString(requestDto);
		// when
		ResultActions resultActions =
			mockMvc.perform(post("/api/issues")
				.header("Authorization", "Bearer " + jwtToken)
				.contentType(MediaType.APPLICATION_JSON)
				.content(request));
		// then
		resultActions.andExpect(status().isOk())
			.andExpect(jsonPath("$.message", containsString("요청이 성공적으로 처리되었습니다.")));
	}
}
