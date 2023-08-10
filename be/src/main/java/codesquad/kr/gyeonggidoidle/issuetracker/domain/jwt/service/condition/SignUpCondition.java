package codesquad.kr.gyeonggidoidle.issuetracker.domain.jwt.service.condition;

import codesquad.kr.gyeonggidoidle.issuetracker.domain.member.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
public class SignUpCondition {

    private final String email;
    private final String password;
    private final String profile;

    @Builder
    private SignUpCondition(String email, String password, String profile) {
        this.email = email;
        this.password = password;
        this.profile = profile;
    }

    public static Member to(SignUpCondition condition) {
        return Member.builder()
                .email(condition.getEmail())
                .password(condition.getPassword())
                .name(Member.findUserName(condition.getEmail()))
                .profile(condition.getProfile())
                .build();
    }
}
