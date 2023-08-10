package codesquad.kr.gyeonggidoidle.issuetracker.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class MemberDuplicationException extends CustomException {

    private static final HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

    public MemberDuplicationException(String email) {
        super(httpStatus, "해당 이메일은 이미 가입된 이메일 입니다. : " + email);
    }
}
