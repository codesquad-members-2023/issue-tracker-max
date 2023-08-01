package codesquad.kr.gyeonggidoidle.issuetracker.domain.member;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Member {

    private final Long id;
    private final String email;
    private final String name;
    private final String password;
    private final String profile;

    @Builder
    private Member(Long id, String email, String name, String password, String profile) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.password = password;
        this.profile = profile;
    }
}
