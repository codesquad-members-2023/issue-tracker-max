package codesquad.kr.gyeonggidoidle.issuetracker.domain.jwt.controller.response;

import codesquad.kr.gyeonggidoidle.issuetracker.domain.jwt.entity.Jwt;
import lombok.Builder;
import lombok.Getter;

@Getter
public class JwtResponse {

    private final String accessToken;

    @Builder
    private JwtResponse(String accessToken) {
        this.accessToken = accessToken;
    }

    public static JwtResponse from(Jwt jwt) {
        return JwtResponse.builder()
                .accessToken(jwt.getAccessToken())
                .build();
    }
}
