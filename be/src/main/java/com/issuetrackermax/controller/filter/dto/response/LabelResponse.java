package com.issuetrackermax.controller.filter.dto.response;

import com.issuetrackermax.domain.label.entity.Label;

import lombok.Builder;
import lombok.Getter;

@Getter
public class LabelResponse {

	private Long id;
	private String title;
	private String textColor;
	private String backgroundColor;

	@Builder
	public LabelResponse(Long id, String title, String textColor, String backgroundColor) {
		this.id = id;
		this.title = title;
		this.textColor = textColor;
		this.backgroundColor = backgroundColor;
	}

	public static LabelResponse from(Label label) {
		return LabelResponse.builder()
			.id(label.getId())
			.title(label.getTitle())
			.textColor(label.getTextColor())
			.backgroundColor(label.getBackgroundColor())
			.build();
	}

}
