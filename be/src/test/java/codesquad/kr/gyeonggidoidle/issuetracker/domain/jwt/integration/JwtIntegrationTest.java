package codesquad.kr.gyeonggidoidle.issuetracker.domain.jwt.integration;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import codesquad.kr.gyeonggidoidle.issuetracker.annotation.IntegrationTest;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.jwt.controller.request.LoginRequest;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.jwt.controller.request.RefreshTokenRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import javax.servlet.http.Cookie;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;


@IntegrationTest
public class JwtIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @DisplayName("이메일과 비밀번호를 받아 JwtResponse를 반환한다.")
    @Test
    void login() throws Exception {
        // given
        LoginRequest request = LoginRequest.builder()
                .email("joy@codesquad.kr")
                .password("1q2w3e4r!")
                .build();

        // when
        ResultActions resultActions = mockMvc.perform(post("/api/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(request)));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(result -> {
                    Cookie[] cookies = result.getResponse().getCookies();
                    Arrays.stream(cookies).anyMatch(cookie -> cookie.getName().equals("refreshToken"));
                })
                .andExpectAll(
                        jsonPath("$.length()").value(2),
                        jsonPath("$.profile").isNotEmpty(),
                        jsonPath("$.jwtResponse.accessToken").isNotEmpty()
                );
    }

    @DisplayName("refreshToken을 받아서 새로운 accessToken이 담긴 JwtResponse를 반환한다.")
    @Test
    void reissueAccessToken() throws Exception {
        // given
        LoginRequest loginRequest = LoginRequest.builder()
                .email("joy@codesquad.kr")
                .password("1q2w3e4r!")
                .build();

        ResultActions loginResult = mockMvc.perform(post("/api/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(loginRequest)));

        final String[] refreshToken = new String[1];

        loginResult.andExpect(result -> {
            Cookie cookie = result.getResponse().getCookie("refreshToken");
            refreshToken[0] = cookie.getValue();
        });

        RefreshTokenRequest request = new RefreshTokenRequest(refreshToken[0]);

        // when
        ResultActions resultActions = mockMvc.perform(post("/api/auth/reissue")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(request)));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.length()").value(1),
                        jsonPath("$.accessToken").isNotEmpty()
                );
    }

    @DisplayName("HttpServletRequest에 담긴 claim의 memberId를 가지고 refreshToken을 삭제한다.")
    @Test
    void logout() throws Exception {
        // given
        LoginRequest loginRequest = LoginRequest.builder()
                .email("joy@codesquad.kr")
                .password("1q2w3e4r!")
                .build();

        ResultActions loginResult = mockMvc.perform(post("/api/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(loginRequest)));

        String jsonResponse = loginResult.andReturn().getResponse().getContentAsString();
        JsonNode jsonNode = new ObjectMapper().readTree(jsonResponse);
        JsonNode jwtResponseNode = jsonNode.get("jwtResponse");
        String accessToken = jwtResponseNode.get("accessToken").asText();

        // when
        ResultActions resultActions = mockMvc.perform(post("/api/logout")
                .header("Authorization", "Bearer " + accessToken));

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(200));
    }

    private <T> String toJson(T data) throws JsonProcessingException {
        return objectMapper.writeValueAsString(data);
    }
}
