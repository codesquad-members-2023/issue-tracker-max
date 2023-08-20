package codesquad.kr.gyeonggidoidle.issuetracker.domain.member.controller.request;

import codesquad.kr.gyeonggidoidle.issuetracker.domain.auth.service.condition.SignUpCondition;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class SignUpRequest {

    @Email(message = "유효한 이메일 주소를 입력하세요.")
    private String email;
    @Size(min = 6, max = 16, message = "비밀번호는 6자리 이상 16자리 이하여야 합니다.")
    private String password;
    @NotBlank
    private String profile;

    @Builder
    public SignUpRequest(String email, String password, String profile) {
        this.email = email;
        this.password = password;
        this.profile = profile;
    }

    public static SignUpCondition to(SignUpRequest request) {
        return SignUpCondition.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .profile(request.getProfile())
                .build();
    }
}
