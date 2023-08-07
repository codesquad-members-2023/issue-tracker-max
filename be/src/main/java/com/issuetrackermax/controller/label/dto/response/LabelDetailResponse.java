package com.issuetrackermax.controller.label.dto.response;

import com.issuetrackermax.domain.label.entity.Label;

import lombok.Builder;
import lombok.Getter;

@Getter
public class LabelDetailResponse {
	private Long id;
	private String name;
	private String description;
	private String textColor;
	private String backgroundColor;

	@Builder
	public LabelDetailResponse(Long id, String name, String description, String textColor, String backgroundColor) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.textColor = textColor;
		this.backgroundColor = backgroundColor;
	}

	public static LabelDetailResponse from(Label label) {
		return LabelDetailResponse
			.builder()
			.id(label.getId())
			.name(label.getTitle())
			.description(label.getDescription())
			.textColor(label.getTextColor())
			.backgroundColor(label.getBackgroundColor())
			.build();
	}
}
