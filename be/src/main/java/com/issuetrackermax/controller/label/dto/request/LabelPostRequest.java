package com.issuetrackermax.controller.label.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LabelPostRequest {
	private String title;
	private String description;
	private String textColor;
	private String backgroundColor;

	@Builder
	public LabelPostRequest(String title, String description, String textColor, String backgroundColor) {
		this.title = title;
		this.description = description;
		this.textColor = textColor;
		this.backgroundColor = backgroundColor;
	}
}
