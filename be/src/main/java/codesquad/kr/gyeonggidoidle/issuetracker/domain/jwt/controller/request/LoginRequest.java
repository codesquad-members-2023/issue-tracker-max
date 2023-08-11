package codesquad.kr.gyeonggidoidle.issuetracker.domain.jwt.controller.request;

import codesquad.kr.gyeonggidoidle.issuetracker.domain.jwt.service.condition.LoginCondition;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
public class LoginRequest {

    @Email
    private final String email;
    @NotBlank
    private final String password;

    @Builder
    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public static LoginCondition to(LoginRequest request) {
        return LoginCondition.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .build();
    }
}
