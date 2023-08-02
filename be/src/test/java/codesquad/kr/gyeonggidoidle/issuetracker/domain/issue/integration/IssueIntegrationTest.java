package codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.integration;

import codesquad.kr.gyeonggidoidle.issuetracker.annotation.IntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@IntegrationTest
public class IssueIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @DisplayName("열린 이슈의 모든 정보를 다 가지고 온다.")
    @Test
    void openIssueIntegrationTest() throws Exception {
        ResultActions resultActions = mockMvc.perform(get("/api/issues/open"));

        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.openIssueCount").value(2))
                .andExpect(jsonPath("$.milestoneCount").value(4))
                .andExpect(jsonPath("$.issues.length()").value(2))
                .andExpect(jsonPath("$.issues.[0].labels.[0].backgroundColor").value("#F08080"))
                .andExpect(jsonPath("$.issues.[1].title").value("제목 1"))
                .andExpect(jsonPath("$.issues.[1].assigneeProfiles.length()").value(2))
                .andDo(print());
    }

    @DisplayName("닫힌 이슈의 모든 정보를 다 가지고 온다.")
    @Test
    void closedIssueIntegrationTest() throws Exception {
        ResultActions resultActions = mockMvc.perform(get("/api/issues/closed"));

        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.closedIssueCount").value(3))
                .andExpect(jsonPath("$.labelCount").value(4))
                .andExpect(jsonPath("$.issues.length()").value(3))
                .andExpect(jsonPath("$.issues.[0].labels.length()").value(0))
                .andExpect(jsonPath("$.issues.[0].assigneeProfiles.length()").value(0))
                .andExpect(jsonPath("$.issues.[1].labels.[0].name").value("라벨 1"))
                .andDo(print());
    }
}
