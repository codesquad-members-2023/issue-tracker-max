package com.issuetrackermax.controller.filter.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class WriterResponse {
	private Long id;
	private String name;
	private String imageUrl;

	@Builder
	public WriterResponse(Long id, String name, String imageUrl) {
		this.id = id;
		this.name = name;
		this.imageUrl = imageUrl;
	}
}
