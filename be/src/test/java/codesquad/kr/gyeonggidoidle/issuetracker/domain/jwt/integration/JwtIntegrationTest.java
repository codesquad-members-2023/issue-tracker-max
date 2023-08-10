package codesquad.kr.gyeonggidoidle.issuetracker.domain.jwt.integration;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import codesquad.kr.gyeonggidoidle.issuetracker.annotation.IntegrationTest;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.jwt.controller.request.LoginRequest;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.jwt.controller.request.SignUpRequest;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.jwt.entity.Jwt;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.jwt.entity.JwtProvider;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
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
    private JwtProvider jwtProvider;
    @Autowired
    ObjectMapper objectMapper;

    @DisplayName("이메일과 비밀번호를 받아 JwtResponse를 반환한다.")
    @Test
    @Order(1)
    void login() throws Exception {
        LoginRequest request = LoginRequest.builder()
                .email("joy@codesquad.kr")
                .password("1q2w3e4r!")
                .build();

        ResultActions resultActions = mockMvc.perform(post("/api/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(request)));

        resultActions
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.length()").value(2),
                        jsonPath("$.accessToken").isNotEmpty(),
                        jsonPath("$.refreshToken").isNotEmpty()
                );
    }

    @DisplayName("이메일, 비밀번호, 프로필사진을 받아 회원가입을 한다.")
    @Test
    @Order(2)
    void signUp() throws Exception {
        SignUpRequest request = SignUpRequest.builder()
                .email("test@test.com")
                .password("!test1234")
                .profile("profile1234")
                .build();

        ResultActions resultActions = mockMvc.perform(post("/api/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(request)));

        resultActions
                .andExpect(status().isOk());
    }

//    @DisplayName("refreshToken을 받아서 새로운 accessToken이 담긴 JwtResponse를 반환한다.")
//    @Test
//    @Order(3)
//    void reissueAccessToken() throws Exception {
//        Jwt jwt = makeToken();
//        RefreshTokenRequest request = new RefreshTokenRequest(jwt.getRefreshToken());
//        ResultActions resultActions = mockMvc.perform(post("/api/auth/reissue")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(toJson(request)));
//
//        resultActions
//                .andExpect(status().isOk())
//                .andExpectAll(
//                        jsonPath("$.length()").value(2),
//                        jsonPath("$.accessToken").isNotEmpty(),
//                        jsonPath("$.refreshToken").isNotEmpty()
//                );
//    }

    @DisplayName("HttpServletRequest에 담긴 claim의 memberId를 가지고 refreshToken을 삭제한다.")
    @Test
    @Order(4)
    void logout() throws Exception {
        Jwt jwt = makeToken();
        ResultActions resultActions = mockMvc.perform(post("/api/logout")
                .header("Authorization", "Bearer " + jwt.getAccessToken()));

        resultActions.andExpect(status().isOk());
    }

    private <T> String toJson(T data) throws JsonProcessingException {
        return objectMapper.writeValueAsString(data);
    }

    private Jwt makeToken() {
        return jwtProvider.createJwt(Map.of("memberId",2L));
    }
}
