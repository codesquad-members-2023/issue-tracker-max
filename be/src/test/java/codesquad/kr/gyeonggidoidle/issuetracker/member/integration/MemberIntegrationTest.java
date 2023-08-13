package codesquad.kr.gyeonggidoidle.issuetracker.member.integration;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import codesquad.kr.gyeonggidoidle.issuetracker.annotation.IntegrationTest;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.member.controller.request.SignUpRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@IntegrationTest
public class MemberIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @DisplayName("이메일, 비밀번호, 프로필사진을 받아 회원가입을 한다.")
    @Test
    void signUp() throws Exception {
        // given
        SignUpRequest request = SignUpRequest.builder()
                .email("test@test.com")
                .password("!test1234")
                .profile("profile1234")
                .build();

        // when
        ResultActions resultActions = mockMvc.perform(post("/api/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(request)));

        // then
        resultActions
                .andExpect(status().isOk());
    }

    private <T> String toJson(T data) throws JsonProcessingException {
        return objectMapper.writeValueAsString(data);
    }
}
