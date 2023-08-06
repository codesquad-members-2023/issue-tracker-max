package codesquad.issueTracker.global.exception;

import org.springframework.http.HttpStatus;

public interface StatusCode {

	HttpStatus getStatus();

	String getMessage();
}
