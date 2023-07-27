package com.issuetrackermax.common.exception;

import lombok.Getter;

@Getter
public class ErrorResponse<T> {
	private boolean success;
	private final T errorCode;

	public ErrorResponse(T errorCode) {
		this.success = false;
		this.errorCode = errorCode;
	}

	public static <T> ErrorResponse<T> exception(T t) {
		return new ErrorResponse<>(t);

	}

}
