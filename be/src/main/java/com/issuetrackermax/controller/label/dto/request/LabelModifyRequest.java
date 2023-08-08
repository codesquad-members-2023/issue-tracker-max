package com.issuetrackermax.controller.label.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LabelModifyRequest {
	private String name;
	private String description;
	private String textColor;
	private String backgroundColor;

	@Builder
	public LabelModifyRequest(String name, String description, String textColor, String backgroundColor) {
		this.name = name;
		this.description = description;
		this.textColor = textColor;
		this.backgroundColor = backgroundColor;
	}
}
