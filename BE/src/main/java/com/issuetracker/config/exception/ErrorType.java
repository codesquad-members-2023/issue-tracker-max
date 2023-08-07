package com.issuetracker.config.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorType {

	LABEL_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 레이블이 존재하지 않습니다."),
	MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 멤버가 존재하지 않습니다."),
	MILESTONE_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 마일스톤이 존재하지 않습니다."),

	QUERY_STRING_KEY_NOT_MATCH(HttpStatus.BAD_REQUEST, "올바른 QueryString Key가 아닙니다.");

	private final HttpStatus status;
	private final String message;
}
