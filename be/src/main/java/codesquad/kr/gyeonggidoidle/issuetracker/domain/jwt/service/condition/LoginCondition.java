package codesquad.kr.gyeonggidoidle.issuetracker.domain.jwt.service.condition;

import lombok.Builder;
import lombok.Getter;

@Getter
public class LoginCondition {

    private final String email;
    private final String password;

    @Builder
    private LoginCondition(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
