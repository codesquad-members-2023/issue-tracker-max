package org.presents.issuetracker.global.error.statuscode;

import org.springframework.http.HttpStatus;

public interface StatusCode {
	String getName();

	HttpStatus getHttpStatus();

	String getMessage();
}
