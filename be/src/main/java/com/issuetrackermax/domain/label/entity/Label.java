package com.issuetrackermax.domain.label.entity;

import com.issuetrackermax.controller.label.dto.request.LabelModifyRequest;
import com.issuetrackermax.controller.label.dto.request.LabelPostRequest;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Label {
	private Long id;
	private String title;
	private String description;
	private String textColor;
	private String backgroundColor;

	@Builder
	public Label(Long id, String title, String description, String textColor, String backgroundColor) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.textColor = textColor;
		this.backgroundColor = backgroundColor;
	}

	public static Label from(LabelModifyRequest labelModifyRequest) {
		return Label.builder()
			.title(labelModifyRequest.getTitle())
			.description(labelModifyRequest.getDescription())
			.textColor(labelModifyRequest.getTextColor())
			.backgroundColor(labelModifyRequest.getBackgroundColor())
			.build();
	}

	public static Label from(LabelPostRequest labelPostRequest) {
		return Label.builder()
			.title(labelPostRequest.getTitle())
			.description(labelPostRequest.getDescription())
			.textColor(labelPostRequest.getTextColor())
			.backgroundColor(labelPostRequest.getBackgroundColor())
			.build();
	}
}
