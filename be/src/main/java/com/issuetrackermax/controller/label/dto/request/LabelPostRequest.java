package com.issuetrackermax.controller.label.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LabelPostRequest {
	private String name;
	private String description;
	private String textColor;
	private String backgroundColor;

	@Builder
	public LabelPostRequest(String name, String description, String textColor, String backgroundColor) {
		this.name = name;
		this.description = description;
		this.textColor = textColor;
		this.backgroundColor = backgroundColor;
	}
}
