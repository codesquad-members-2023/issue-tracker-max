package codesquad.kr.gyeonggidoidle.issuetracker.domain.member.controller.request;

import codesquad.kr.gyeonggidoidle.issuetracker.domain.jwt.service.condition.SignUpCondition;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class SignUpRequest {

    @Email
    private String email;
    @NotBlank
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
