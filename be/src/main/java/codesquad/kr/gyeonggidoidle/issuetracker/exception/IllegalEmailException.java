package codesquad.kr.gyeonggidoidle.issuetracker.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class IllegalEmailException extends CustomException {

    private static final HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

    public IllegalEmailException() {
        super(httpStatus, "형식에 맞지 않는 이메일입니다.");
    }
}
