package codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.integration;

import codesquad.kr.gyeonggidoidle.issuetracker.annotation.IntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@IntegrationTest
class IssueIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

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
}
