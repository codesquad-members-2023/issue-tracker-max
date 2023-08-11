package org.presents.issuetracker.global.dto.response;

import lombok.Getter;

@Getter
public class LabelIdResponse {
	private Long id;

	private LabelIdResponse(Long id) {
		this.id = id;
	}

	public static LabelIdResponse from(Long id) {
		return new LabelIdResponse(id);
	}
}
