package codesquad.kr.gyeonggidoidle.issuetracker.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class MemberNotFoundException extends CustomException {

    private static final HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

    public MemberNotFoundException() {
        super(httpStatus, "존재하지 않는 회원입니다.");
    }
}
