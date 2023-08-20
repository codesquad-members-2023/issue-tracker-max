package codesquad.kr.gyeonggidoidle.issuetracker.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class FileUploadFailedException extends CustomException {

    private static final HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

    public FileUploadFailedException() {
        super(httpStatus, "파일 업로드에 실패했습니다.");
    }
}
