package codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.integration;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import codesquad.kr.gyeonggidoidle.issuetracker.annotation.IntegrationTest;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.controller.request.IssueCreateRequest;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.controller.request.IssueStatusRequest;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.controller.request.IssueUpdateRequest;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.jwt.entity.Jwt;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.jwt.entity.JwtProvider;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@IntegrationTest
class IssueIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("열린 이슈의 모든 정보를 다 가지고 온다.")
    @Test
    void getOpenIssues() throws Exception {
        // given
        Jwt jwt = makeToken();

        //when
        ResultActions resultActions = mockMvc.perform(get("/api/issues/open")
                .header("Authorization", "Bearer " + jwt.getAccessToken()));

        //then
        resultActions
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.openIssueCount").value(2),
                        jsonPath("$.milestoneCount").value(4),
                        jsonPath("$.issues.length()").value(2),
                        jsonPath("$.issues.[0].labels.[0].backgroundColor").value("#F08080"),
                        jsonPath("$.issues.[1].title").value("제목 1"),
                        jsonPath("$.issues.[1].assigneeProfiles.length()").value(2)
                );
    }

    @DisplayName("닫힌 이슈의 모든 정보를 다 가지고 온다.")
    @Test
    void getClosedIssues() throws Exception {
        // given
        Jwt jwt = makeToken();

        //when
        ResultActions resultActions = mockMvc.perform(get("/api/issues/closed")
                .header("Authorization", "Bearer " + jwt.getAccessToken()));

        //then
        resultActions
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.closedIssueCount").value(3),
                        jsonPath("$.labelCount").value(4),
                        jsonPath("$.issues.length()").value(3),
                        jsonPath("$.issues.[0].labels.length()").value(0),
                        jsonPath("$.issues.[0].assigneeProfiles.length()").value(0),
                        jsonPath("$.issues.[1].labels.[0].name").value("라벨 1")
                );
    }

    @DisplayName("메인 화면의 필터 목록을 가지고 온다.")
    @Test
    void getFilters() throws Exception {
        // given
        Jwt jwt = makeToken();

        //when
        ResultActions resultActions = mockMvc.perform(get("/api/filters")
                .header("Authorization", "Bearer " + jwt.getAccessToken()));

        //then
        resultActions
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.assignees.length()").value(4),
                        jsonPath("$.authors.length()").value(3),
                        jsonPath("$.labels.length()").value(4),
                        jsonPath("$.milestones.length()").value(4),
                        jsonPath("$.assignees.[0].name").value("담당자가 없는 이슈")
                );
    }

    @DisplayName("이슈 화면의 필터 목록을 가지고 온다.")
    @Test
    void getFiltersByIssue() throws Exception {
        // given
        Jwt jwt = makeToken();

        //when
        ResultActions resultActions = mockMvc.perform(get("/api/issues")
                .header("Authorization", "Bearer " + jwt.getAccessToken()));

        //then
        resultActions
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.assignees.length()").value(3),
                        jsonPath("$.authors.length()").value(0),
                        jsonPath("$.labels.length()").value(4),
                        jsonPath("$.milestones.length()").value(4),
                        jsonPath("$.milestones.[0].openIssueCount").value(0),
                        jsonPath("$.milestones.[0].closedIssueCount").value(0),
                        jsonPath("$.milestones.[1].openIssueCount").value(1),
                        jsonPath("$.milestones.[1].closedIssueCount").value(2)
                );
    }

    @DisplayName("선택한 여러 개의 이슈 상태를 변경한다.")
    @Test
    void testUpdateIssuesStatusIntegrationTest() throws Exception {
        // given
        IssueStatusRequest request = IssueStatusRequest.builder()
                .open(false)
                .issues(List.of(4L, 5L))
                .build();
        Jwt jwt = makeToken();

        // when
        ResultActions resultActions = mockMvc.perform(patch("/api/issues")
                .header("Authorization", "Bearer " + jwt.getAccessToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(request)));

        // then
        resultActions.andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("선택한 이슈를 삭제한다.")
    @Test
    void testDeleteIssue() throws Exception {
        // given
        Jwt jwt = makeToken();

        // when
        ResultActions resultActions = mockMvc.perform(delete("/api/issues/1")
                .header("Authorization", "Bearer " + jwt.getAccessToken()));

        // then
        resultActions.andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("선택한 하나의 이슈 상태를 변경한다.")
    @Test
    void testUpdateIssueStatus() throws Exception {
        // given
        IssueStatusRequest request = IssueStatusRequest.builder()
                .open(false)
                .build();
        Jwt jwt = makeToken();

        // when
        ResultActions resultActions = mockMvc.perform(patch("/api/issues/2")
                .header("Authorization", "Bearer " + jwt.getAccessToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(request)));
        // then
        resultActions.andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("이슈를 생성한다.")
    @Test
    void testCreate() throws Exception {
        // given
        IssueCreateRequest request = IssueCreateRequest.builder()
                .title("제목")
                .authorId(1L)
                .assignees(List.of(1L))
                .labels(List.of(2L))
                .milestone(1L)
                .build();
        Jwt jwt = makeToken();

        // when
        ResultActions resultActions = mockMvc.perform(post("/api/issues")
                .header("Authorization", "Bearer " + jwt.getAccessToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(request)));

        // then
        resultActions.andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("이슈의 내용을 수정한다.")
    @Test
    void testUpdateIssue() throws Exception {
        // given
        IssueUpdateRequest request = IssueUpdateRequest.builder()
                .title("수정된 제목")
                .assignees(List.of(1L, 2L))
                .labels(List.of(2L))
                .milestone(1L)
                .build();
        Jwt jwt = makeToken();

        // when
        ResultActions resultActions = mockMvc.perform(put("/api/issues/1")
                .header("Authorization", "Bearer " + jwt.getAccessToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(request)));

        // then
        resultActions.andExpect(status().isOk())
                .andDo(print());
    }

    private <T> String toJson(T data) throws JsonProcessingException {
        return objectMapper.writeValueAsString(data);
    }

    private Jwt makeToken() {
        return jwtProvider.createJwt(Map.of("memberId", 1L));
    }
}
