package codesquad.issueTracker.user.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class LoginResponseDto {

    private String accessToken;
    private String refreshToken;

}
