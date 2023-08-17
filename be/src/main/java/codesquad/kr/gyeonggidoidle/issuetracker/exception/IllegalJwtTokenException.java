package codesquad.kr.gyeonggidoidle.issuetracker.exception;

import codesquad.kr.gyeonggidoidle.issuetracker.domain.auth.controller.response.JwtTokenType;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class IllegalJwtTokenException extends CustomException {

    private static final HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;

    public IllegalJwtTokenException(JwtTokenType tokenType) {
        super(httpStatus, "[Token 인증 오류]: 잘못된 " + tokenType.name() + " 토큰 입니다.");
    }
}
