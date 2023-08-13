package codesquad.kr.gyeonggidoidle.issuetracker.domain.jwt.controller.response;

import codesquad.kr.gyeonggidoidle.issuetracker.domain.jwt.entity.Jwt;
import lombok.Builder;
import lombok.Getter;

@Getter
public class JwtResponse {

    private final String accessToken;
    private final String refreshToken;

    @Builder
    private JwtResponse(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public static JwtResponse from(Jwt jwt) {
        return JwtResponse.builder()
                .accessToken(jwt.getAccessToken())
                .refreshToken(jwt.getRefreshToken())
                .build();
    }
}
