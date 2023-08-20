package com.issuetrackermax.controller.label.dto.response;

import com.issuetrackermax.domain.label.entity.Label;

import lombok.Builder;
import lombok.Getter;

@Getter
public class LabelContentResponse {
	private final Long id;
	private final String title;
	private final String textColor;
	private final String backgroundColor;

	@Builder
	public LabelContentResponse(Long id, String title, String textColor, String backgroundColor) {
		this.id = id;
		this.title = title;
		this.textColor = textColor;
		this.backgroundColor = backgroundColor;
	}

	public static LabelContentResponse from(Label label) {
		return LabelContentResponse
			.builder()
			.id(label.getId())
			.title(label.getTitle())
			.textColor(label.getTextColor())
			.backgroundColor(label.getBackgroundColor())
			.build();
	}
}
