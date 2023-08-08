package codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.integration;

import codesquad.kr.gyeonggidoidle.issuetracker.annotation.IntegrationTest;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.contoller.request.IssueCreateRequest;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.contoller.request.IssueStatusRequest;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.contoller.request.IssueUpdateRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@IntegrationTest
class IssueIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @DisplayName("열린 이슈의 모든 정보를 다 가지고 온다.")
    @Test
    void getOpenIssues() throws Exception {
        //when
        ResultActions resultActions = mockMvc.perform(get("/api/issues/open"));

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
        //when
        ResultActions resultActions = mockMvc.perform(get("/api/issues/closed"));

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
        //when
        ResultActions resultActions = mockMvc.perform(get("/api/filters"));

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
        //when
        ResultActions resultActions = mockMvc.perform(get("/api/issues"));

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

        IssueStatusRequest request = IssueStatusRequest.builder()
                .open(false)
                .issues(List.of(4L,5L))
                .build();

        ResultActions resultActions = mockMvc.perform(patch("/api/issues")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(request)));

        resultActions.andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("선택한 이슈를 삭제한다.")
    @Test
    void testDeleteIssue() throws Exception {
        ResultActions resultActions = mockMvc.perform(delete("/api/issues/1"));

        resultActions.andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("선택한 하나의 이슈 상태를 변경한다.")
    @Test
    void testUpdateIssueStatus() throws Exception {
        IssueStatusRequest request = IssueStatusRequest.builder()
                .open(false)
                .build();

        ResultActions resultActions = mockMvc.perform(patch("/api/issues/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(request)));

        resultActions.andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("이슈를 생성한다.")
    @Test
    void testCreate() throws Exception {
        IssueCreateRequest request = IssueCreateRequest.builder()
                .title("제목")
                .authorId(1L)
                .assignees(List.of(1L))
                .labels(List.of(2L))
                .milestone(1L)
                .build();

        ResultActions resultActions = mockMvc.perform(post("/api/issues")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(request)));

        resultActions.andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("이슈의 내용을 수정한다.")
    @Test
    void testUpdateIssue() throws Exception {
        IssueUpdateRequest request = IssueUpdateRequest.builder()
                .title("수정된 제목")
                .assignees(List.of(1L,2L))
                .labels(List.of(2L))
                .milestone(1L)
                .build();

        ResultActions resultActions = mockMvc.perform(put("/api/issues/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(request)));

        resultActions.andExpect(status().isOk())
                .andDo(print());
    }

    private <T> String toJson(T data) throws JsonProcessingException {
        return objectMapper.writeValueAsString(data);
    }
}
