package codesquad.kr.gyeonggidoidle.issuetracker.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class IllegalPasswordException extends CustomException {

    private static final HttpStatus httpstatus = HttpStatus.UNAUTHORIZED;

    public IllegalPasswordException() {
        super(httpstatus, "비밀번호가 일치하지 않거나 존재하지 않는 회원입니다.");
    }
}
