package codesquad.kr.gyeonggidoidle.issuetracker.domain.jwt.controller.request;

import codesquad.kr.gyeonggidoidle.issuetracker.domain.jwt.service.condition.SignUpCondition;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
public class SignUpRequest {

    @Email
    private final String email;
    @NotBlank
    private final String password;
    @NotBlank
    private final String profile;

    @Builder
    private SignUpRequest(String email, String password, String profile) {
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
