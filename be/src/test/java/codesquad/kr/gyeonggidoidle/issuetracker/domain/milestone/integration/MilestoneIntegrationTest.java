package codesquad.kr.gyeonggidoidle.issuetracker.domain.milestone.integration;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import codesquad.kr.gyeonggidoidle.issuetracker.annotation.IntegrationTest;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.jwt.entity.Jwt;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.jwt.entity.JwtProvider;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.milestone.controller.request.MilestoneRequest;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.milestone.controller.request.MilestoneStatusRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@IntegrationTest
class MilestoneIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("열린 마일스톤의 모드 정보를 가지고 온다.")
    @Test
    void getOpenMilestones() throws Exception {
        // given
        Jwt jwt = makeToken();

        //when
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
        // given
        Jwt jwt = makeToken();

        //when
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

    @DisplayName("마일스톤을 받아 저장한다.")
    @Test
    void create() throws Exception {
        // given
        MilestoneRequest request = MilestoneRequest.builder()
                .name("name")
                .description("설명")
                .dueDate(LocalDate.now())
                .build();
        Jwt jwt = makeToken();

        // when
        ResultActions resultActions = mockMvc.perform(post("/api/milestones")
                .header("Authorization", "Bearer " + jwt.getAccessToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(request)));

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(200))
                .andDo(print());
    }

    @DisplayName("마일스톤을 받아 내용을 수정한다.")
    @Test
    void update() throws Exception {
        // given
        MilestoneRequest request = MilestoneRequest.builder()
                .name("updatedTitle")
                .description("설명")
                .dueDate(LocalDate.now())
                .build();
        Jwt jwt = makeToken();

        // when
        ResultActions resultActions = mockMvc.perform(put("/api/milestones/1")
                .header("Authorization", "Bearer " + jwt.getAccessToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(request)));

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(200))
                .andDo(print());
    }

    @DisplayName("마일스톤 아이디를 받아 마일스톤을 삭제한다.")
    @Test
    void delete() throws Exception {
        // given
        Jwt jwt = makeToken();

        // when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.delete("/api/milestones/3")
                .header("Authorization", "Bearer " + jwt.getAccessToken()));

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(200))
                .andDo(print());
    }

    @DisplayName("마일스톤 아이디와 변경 할 상태를 받아 마일스톤의 상태를 변경한다.")
    @Test
    void updateStatus() throws Exception {
        // given
        MilestoneStatusRequest request = new MilestoneStatusRequest(false);
        Jwt jwt = makeToken();

        // when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.patch("/api/milestones/3")
                .header("Authorization", "Bearer " + jwt.getAccessToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(request)));

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(200))
                .andDo(print());
    }

    private Jwt makeToken() {
        return jwtProvider.createJwt(Map.of("memberId",1L));
    }

    private <T> String toJson(T data) throws JsonProcessingException {
        return objectMapper.writeValueAsString(data);
    }
}
