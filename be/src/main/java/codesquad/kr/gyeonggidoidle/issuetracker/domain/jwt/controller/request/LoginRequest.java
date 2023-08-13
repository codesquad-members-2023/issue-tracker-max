package codesquad.kr.gyeonggidoidle.issuetracker.domain.jwt.controller.request;

import codesquad.kr.gyeonggidoidle.issuetracker.domain.jwt.service.condition.LoginCondition;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class LoginRequest {

    @Email
    private String email;
    @NotBlank
    private String password;

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
