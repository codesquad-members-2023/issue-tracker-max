package com.issuetrackermax.controller.label.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class LabelPostResponse {
	Long id;

	@Builder
	public LabelPostResponse(Long id) {
		this.id = id;
	}
}
