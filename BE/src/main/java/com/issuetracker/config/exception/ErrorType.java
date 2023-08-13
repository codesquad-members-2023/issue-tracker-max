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
	ISSUE_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 이슈가 존재하지 않습니다."),
	FILE_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 파일을 찾을 수 없습니다."),
	ISSUE_COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 이슈 댓글을 찾을 수 없습니다."),

	QUERY_STRING_KEY_NOT_MATCH(HttpStatus.BAD_REQUEST, "올바른 QueryString Key가 아닙니다."),

	ILLEGAL_ID(HttpStatus.BAD_REQUEST, "유효한 id값이 아닙니다."),
	ILLEGAL_BOOLEAN_VALUE(HttpStatus.BAD_REQUEST, "유효한 boolean값이 아닙니다."),

	FILE_UPLOAD_MAX_SIZE(HttpStatus.BAD_REQUEST, "이미지 파일 용량은 5MB 이하만 가능합니다."),
	FILE_CONTENT_TYPE(HttpStatus.BAD_REQUEST, "이미지 파일만 업로드 가능합니다."),
	ISSUE_UPDATE_NULL(HttpStatus.BAD_REQUEST, "수정할 데이터가 존재하지 않습니다."),
	ASSIGNEE_DUPLICATION(HttpStatus.BAD_REQUEST, "이미 등록된 담당자입니다."),
	ASSIGNED_LABEL_DUPLICATION(HttpStatus.BAD_REQUEST, "이미 등록된 라벨입니다.");

	private final HttpStatus status;
	private final String message;
}
