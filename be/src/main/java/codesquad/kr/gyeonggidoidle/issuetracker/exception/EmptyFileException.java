package codesquad.kr.gyeonggidoidle.issuetracker.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class EmptyFileException extends CustomException {

    private static final HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

    public EmptyFileException() {
        super(httpStatus, "빈 파일 입니다.");
    }
}
