package com.issuetrackermax.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorCode {
	private final int status;
	private final String message;
}
