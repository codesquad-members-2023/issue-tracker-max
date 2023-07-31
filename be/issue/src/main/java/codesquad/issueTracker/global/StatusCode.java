package codesquad.issueTracker.global;

import org.springframework.http.HttpStatus;

public interface StatusCode {

    HttpStatus getStatus();

    String getMessage();
}
