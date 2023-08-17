package com.issuetracker.label.application.dto;

import com.issuetracker.label.domain.Label;

import lombok.Getter;

@Getter
public class LabelUpdateInputData {

	private Long id;
	private String title;
	private String description;
	private String backgroundColor;
	private String textColor;

	public LabelUpdateInputData(Long id, String title, String description, String backgroundColor, String textColor) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.backgroundColor = backgroundColor;
		this.textColor = textColor;
	}

	public Label toLabelForUpdate() {
		return Label.builder()
			.id(id)
			.title(title)
			.description(description)
			.backgroundColor(backgroundColor)
			.textColor(textColor)
			.build();
	}
}
