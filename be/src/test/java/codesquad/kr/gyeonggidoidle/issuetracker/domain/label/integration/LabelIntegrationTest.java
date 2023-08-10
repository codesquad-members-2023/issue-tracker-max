package codesquad.kr.gyeonggidoidle.issuetracker.domain.label.integration;

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
class LabelIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtProvider jwtProvider;

    @DisplayName("라벨의 모드 정보를 가지고 온다.")
    @Test
    void getLabels() throws Exception {
        //when
        Jwt jwt = makeToken();
        ResultActions resultActions = mockMvc.perform(get("/api/labels")
                .header("Authorization", "Bearer " + jwt.getAccessToken()));

        //then
        resultActions
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.milestoneCount").value(4),
                        jsonPath("$.labelCount").value(4),
                        jsonPath("$.labels.length()").value(4),
                        jsonPath("$.labels.[0].name").value("라벨 0")
                );
    }

    private Jwt makeToken() {
        return jwtProvider.createJwt(Map.of("memberId",1L));
    }
}
