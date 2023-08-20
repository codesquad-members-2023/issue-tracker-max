package codesquad.kr.gyeonggidoidle.issuetracker.domain.auth.controller.response;

import codesquad.kr.gyeonggidoidle.issuetracker.domain.auth.service.information.JwtLoginInformation;
import lombok.Builder;
import lombok.Getter;

@Getter
public class JwtLoginResponse {

    private final Long id;
    private final String name;
    private final String profile;
    private final JwtResponse jwtResponse;

    @Builder
    private JwtLoginResponse(Long id, String name, String profile, JwtResponse jwtResponse) {
        this.id = id;
        this.name = name;
        this.profile = profile;
        this.jwtResponse = jwtResponse;
    }

    public static JwtLoginResponse from(JwtLoginInformation information) {
        return JwtLoginResponse.builder()
                .id(information.getId())
                .name(information.getName())
                .profile(information.getProfile())
                .jwtResponse(JwtResponse.from(information.getJwt()))
                .build();
    }
}
