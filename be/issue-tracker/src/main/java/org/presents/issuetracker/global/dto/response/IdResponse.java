package org.presents.issuetracker.global.dto.response;

import lombok.Getter;

@Getter
public class IdResponse {
	private Long id;

	private IdResponse(Long id) {
		this.id = id;
	}

	public static IdResponse from(Long id) {
		return new IdResponse(id);
	}
}
