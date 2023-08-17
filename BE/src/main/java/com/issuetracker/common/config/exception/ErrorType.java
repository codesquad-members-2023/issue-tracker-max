package com.issuetracker.common.config.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorType {

	// LABEL
	LABEL_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 레이블이 존재하지 않습니다."),
	LABEL_TITLE_DUPLICATION(HttpStatus.BAD_REQUEST, "이미 등록된 제목입니다."),

	// MILESTONE
	MILESTONE_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 마일스톤이 존재하지 않습니다."),
	MILESTONE_TITLE_DUPLICATION(HttpStatus.BAD_REQUEST, "이미 등록된 제목입니다."),

	// MEMBER
	MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 멤버가 존재하지 않습니다."),
	ACCOUNT_EMAIL_DUPLICATION(HttpStatus.BAD_REQUEST, "이미 사용중인 email 입니다."),

	// ASSIGNEE
	ASSIGNEE_DUPLICATION(HttpStatus.BAD_REQUEST, "이미 등록된 담당자입니다."),
	ASSIGNED_LABEL_DUPLICATION(HttpStatus.BAD_REQUEST, "이미 등록된 라벨입니다."),

	// ISSUE
	ISSUE_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 이슈가 존재하지 않습니다."),
	ISSUE_COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 이슈 댓글을 찾을 수 없습니다."),
	ISSUE_UPDATE_NULL(HttpStatus.BAD_REQUEST, "수정할 데이터가 존재하지 않습니다."),

	// FILE
	FILE_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 파일을 찾을 수 없습니다."),
	FILE_UPLOAD_MAX_SIZE(HttpStatus.BAD_REQUEST, "이미지 파일 용량은 5MB 이하만 가능합니다."),
	FILE_CONTENT_TYPE(HttpStatus.BAD_REQUEST, "이미지 파일만 업로드 가능합니다."),

	// REQUEST
	ILLEGAL_ID(HttpStatus.BAD_REQUEST, "유효한 id값이 아닙니다."),
	ILLEGAL_BOOLEAN_VALUE(HttpStatus.BAD_REQUEST, "유효한 boolean값이 아닙니다."),
	QUERY_STRING_KEY_NOT_MATCH(HttpStatus.BAD_REQUEST, "올바른 QueryString Key가 아닙니다."),
	QUERY_STRING_VALUE_NOT_MATCH(HttpStatus.BAD_REQUEST, "올바른 QueryString Value가 아닙니다."),

	// TOKEN
	NO_REFRESH_TOKEN(HttpStatus.NOT_FOUND, "해당 리프레시 토큰이 존재하지 않습니다."),
	EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "기한이 만료된 토큰입니다."),

	// Exception
	NO_HANDLER_FOUND(HttpStatus.NOT_FOUND, "해당 API 경로로는 요청할 수 없습니다."),
	HTTP_MESSAGE_NOT_READABLE(HttpStatus.BAD_REQUEST, "JSON 필드 값은 String, Number, Array, Object 객체 또는 'null', 'true', 'false'만 입력 가능합니다."),
	SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 에러입니다");

	private final HttpStatus status;
	private final String message;
}
