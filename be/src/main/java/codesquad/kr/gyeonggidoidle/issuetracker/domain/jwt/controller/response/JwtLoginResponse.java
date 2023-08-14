package codesquad.kr.gyeonggidoidle.issuetracker.domain.jwt.controller.response;

import codesquad.kr.gyeonggidoidle.issuetracker.domain.jwt.service.information.JwtLoginInformation;
import lombok.Builder;
import lombok.Getter;

@Getter
public class JwtLoginResponse {

    private final String profile;
    private final JwtResponse jwtResponse;

    @Builder
    private JwtLoginResponse(String profile, JwtResponse jwtResponse) {
        this.profile = profile;
        this.jwtResponse = jwtResponse;
    }

    public static JwtLoginResponse from(JwtLoginInformation information) {
        return JwtLoginResponse.builder()
                .profile(information.getProfile())
                .jwtResponse(JwtResponse.from(information.getJwt()))
                .build();
    }
}
