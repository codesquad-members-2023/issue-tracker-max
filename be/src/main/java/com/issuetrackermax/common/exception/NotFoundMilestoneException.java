package com.issuetrackermax.common.exception;

import org.springframework.http.HttpStatus;

public class NotFoundMilestoneException extends ApiException {
	public NotFoundMilestoneException() {
		super(HttpStatus.NOT_FOUND, "존재하지 않는 마일스톤입니다.");
	}

}
