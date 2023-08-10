package codesquad.kr.gyeonggidoidle.issuetracker.domain.milestone.integration;

import codesquad.kr.gyeonggidoidle.issuetracker.annotation.IntegrationTest;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.jwt.entity.Jwt;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.jwt.entity.JwtProvider;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@IntegrationTest
class MilestoneIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtProvider jwtProvider;

    @DisplayName("열린 마일스톤의 모드 정보를 가지고 온다.")
    @Test
    void getOpenMilestones() throws Exception {
        //when
        Jwt jwt = makeToken();
        ResultActions resultActions = mockMvc.perform(get("/api/milestones/open")
                .header("Authorization", "Bearer " + jwt.getAccessToken()));

        //then
        resultActions
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.openMilestoneCount").value(3),
                        jsonPath("$.closeMilestoneCount").value(1),
                        jsonPath("$.labelCount").value(4),
                        jsonPath("$.milestones.length()").value(3),
                        jsonPath("$.milestones.[0].name").value("마일스톤 0")
                );
    }

    @DisplayName("닫힌 마일스톤의 모드 정보를 가지고 온다.")
    @Test
    void readClosedMilestones() throws Exception {
        //when
        Jwt jwt = makeToken();
        ResultActions resultActions = mockMvc.perform(get("/api/milestones/closed")
                .header("Authorization", "Bearer " + jwt.getAccessToken()));

        //then
        resultActions
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.openMilestoneCount").value(3),
                        jsonPath("$.closeMilestoneCount").value(1),
                        jsonPath("$.labelCount").value(4),
                        jsonPath("$.milestones.length()").value(1),
                        jsonPath("$.milestones.[0].name").value("마일스톤 2")
                );
    }

    private Jwt makeToken() {
        return jwtProvider.createJwt(Map.of("memberId",1L));
    }
}
