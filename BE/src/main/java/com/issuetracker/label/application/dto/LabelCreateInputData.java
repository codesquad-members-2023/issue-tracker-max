package com.issuetracker.label.application.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.issuetracker.label.domain.Label;

import lombok.Getter;

@Getter
public class LabelCreateInputData {

	private String title;
	private String description;
	private String backgroundColor;
	private String textColor;

	public LabelCreateInputData(String title, String description, String backgroundColor, String textColor) {
		this.title = title;
		this.description = description;
		this.backgroundColor = backgroundColor;
		this.textColor = textColor;
	}

	public Label toLabelForCreate() {
		return Label.builder()
			.title(title)
			.description(description)
			.backgroundColor(backgroundColor)
			.textColor(textColor)
			.build();
	}
}
