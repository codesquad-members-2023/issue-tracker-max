package org.presents.issuetracker.global.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class LabelResponse {
	private Long id;

	@Builder
	public LabelResponse(Long id) {
		this.id = id;
	}
}
