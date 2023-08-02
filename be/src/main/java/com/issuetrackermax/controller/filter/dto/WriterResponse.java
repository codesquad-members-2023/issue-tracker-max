package com.issuetrackermax.controller.filter.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class WriterResponse {
	private Long id;
	private String name;

	@Builder
	public WriterResponse(Long id, String name) {
		this.id = id;
		this.name = name;
	}
}
