package com.issuetrackermax.controller;

import lombok.Getter;

@Getter
public class ApiResponse<T> {
	private boolean success;
	private T data;

	public ApiResponse(boolean success, T data) {
		this.success = success;
		this.data = data;
	}

	public ApiResponse(boolean success) {
		this.success = success;
	}

	public ApiResponse(T data) {
		this.data = data;
	}

	public static <T> ApiResponse<T> of(boolean success, T data) {
		return new ApiResponse<>(success, data);
	}

	public static <T> ApiResponse<T> ok(T data) {
		return of(true, data);
	}

	public static <T> ApiResponse<T> success() {
		return new ApiResponse<>(true);
	}

	public static <T> ApiResponse<T> success(T data) {
		return new ApiResponse<>(true, data);
	}

	public static <ErrorResponse> ApiResponse<ErrorResponse> exception(ErrorResponse errorResponse) {
		return new ApiResponse<>(false, errorResponse);
	}
}
