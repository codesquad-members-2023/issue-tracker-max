package codesquad.kr.gyeonggidoidle.issuetracker.domain.jwt.controller.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RefreshTokenRequest {

    private String refreshToken;

    public RefreshTokenRequest(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public RefreshTokenRequest(){}
}
