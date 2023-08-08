package com.issuetracker.label.application.dto;

import com.issuetracker.label.domain.Label;

import lombok.Getter;

@Getter
public class LabelCreateInputData {

	private String title;
	private String description;
	private String color;

	public LabelCreateInputData(String title, String description, String color) {
		this.title = title;
		this.description = description;
		this.color = color;
	}

	public Label toLabelForCreate() {
		return Label.builder()
			.title(title)
			.description(description)
			.color(color)
			.build();
	}
}
