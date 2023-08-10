package codesquad.kr.gyeonggidoidle.issuetracker.domain.member;

import codesquad.kr.gyeonggidoidle.issuetracker.exception.IllegalEmailException;
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

    public static String findUserName(String email) {
        int idx = email.indexOf('@');
        if (idx == -1) {
            throw new IllegalEmailException();
        }
        return email.substring(0,idx);
    }
}
