package com.issuetrackermax.controller.label.dto.response;

import com.issuetrackermax.domain.label.entity.Label;

import lombok.Builder;
import lombok.Getter;

@Getter
public class LabelDetailResponse {
	private Long id;
	private String title;
	private String description;
	private String textColor;
	private String backgroundColor;

	@Builder
	public LabelDetailResponse(Long id, String title, String description, String textColor, String backgroundColor) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.textColor = textColor;
		this.backgroundColor = backgroundColor;
	}

	public static LabelDetailResponse from(Label label) {
		return LabelDetailResponse
			.builder()
			.id(label.getId())
			.title(label.getTitle())
			.description(label.getDescription())
			.textColor(label.getTextColor())
			.backgroundColor(label.getBackgroundColor())
			.build();
	}
}
