package codesquad.kr.gyeonggidoidle.issuetracker.domain.label.integration;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import codesquad.kr.gyeonggidoidle.issuetracker.annotation.IntegrationTest;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.auth.entity.Jwt;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.auth.entity.JwtProvider;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.label.controller.request.LabelRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@IntegrationTest
class LabelIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("라벨의 모드 정보를 가지고 온다.")
    @Test
    void getLabels() throws Exception {
        // given
        Jwt jwt = makeToken();

        //when
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

    @DisplayName("라벨을 받아 저장한다.")
    @Test
    void create() throws Exception {
        // given
        LabelRequest request = LabelRequest.builder()
                .name("label1")
                .description("설명")
                .backgroundColor("##")
                .textColor("#")
                .build();
        Jwt jwt = makeToken();

        // when
        ResultActions resultActions = mockMvc.perform(post("/api/labels")
                .header("Authorization", "Bearer " + jwt.getAccessToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(request)));

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(200))
                .andDo(print());
    }

    @DisplayName("하나의 라벨 정보를 가져온다.")
    @Test
    void read() throws Exception {
        // given
        Jwt jwt = makeToken();

        // when
        ResultActions resultActions = mockMvc.perform(get("/api/labels/1")
                .header("Authorization", "Bearer " + jwt.getAccessToken()));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.id").value(1L),
                        jsonPath("$.name").value("라벨 1"),
                        jsonPath("$.description").doesNotExist()
                );
    }

    @DisplayName("라벨을 받아 내용을 수정한다.")
    @Test
    void update() throws Exception {
        // given
        LabelRequest request = LabelRequest.builder()
                .name("updatedTitle")
                .description("설명")
                .backgroundColor("##")
                .textColor("#")
                .build();
        Jwt jwt = makeToken();

        // when
        ResultActions resultActions = mockMvc.perform(patch("/api/labels/1")
                .header("Authorization", "Bearer " + jwt.getAccessToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(request)));

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(200))
                .andDo(print());
    }

    @DisplayName("라벨 아이디를 받아 라벨을 삭제한다.")
    @Test
    void delete() throws Exception {
        // given
        Jwt jwt = makeToken();

        // when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.delete("/api/labels/3")
                .header("Authorization", "Bearer " + jwt.getAccessToken()));

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
