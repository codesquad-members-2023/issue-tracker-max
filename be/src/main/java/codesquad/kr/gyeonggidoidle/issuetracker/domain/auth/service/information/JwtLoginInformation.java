package codesquad.kr.gyeonggidoidle.issuetracker.domain.auth.service.information;

import codesquad.kr.gyeonggidoidle.issuetracker.domain.auth.entity.Jwt;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.member.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
public class JwtLoginInformation {

    private final Long id;
    private final String name;
    private final String profile;
    private final Jwt jwt;

    @Builder
    public JwtLoginInformation(Long id, String name, String profile, Jwt jwt) {
        this.id = id;
        this.name = name;
        this.profile = profile;
        this.jwt = jwt;
    }

    public static JwtLoginInformation from(Member member, Jwt jwt) {
        return JwtLoginInformation.builder()
                .id(member.getId())
                .name(member.getName())
                .profile(member.getProfile())
                .jwt(jwt)
                .build();
    }
}
