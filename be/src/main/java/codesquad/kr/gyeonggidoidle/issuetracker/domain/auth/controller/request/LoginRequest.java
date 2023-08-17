package codesquad.kr.gyeonggidoidle.issuetracker.domain.auth.controller.request;

import codesquad.kr.gyeonggidoidle.issuetracker.domain.auth.service.condition.LoginCondition;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class LoginRequest {

    @NotBlank(message = "이메일을 입력해주세요.")
    @Email(message = "유효한 이메일 주소를 입력하세요.")
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Size(min = 6, max = 16, message = "비밀번호는 6자리 이상 16자리 이하여야 합니다.")
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
