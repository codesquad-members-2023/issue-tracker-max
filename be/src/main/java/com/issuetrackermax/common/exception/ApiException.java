package com.issuetrackermax.common.exception;

import com.issuetrackermax.common.exception.domain.CustomException;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ApiException extends RuntimeException {
	private final CustomException customException;

	@Builder
	public ApiException(CustomException customException) {
		this.customException = customException;
	}
}
