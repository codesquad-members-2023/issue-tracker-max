package com.issuetracker.label.application.dto;

import com.issuetracker.label.domain.Label;

import lombok.Getter;

@Getter
public class LabelUpdateInputData {

	private Long id;
	private String title;
	private String description;
	private String color;

	public LabelUpdateInputData(Long id, String title, String description, String color) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.color = color;
	}

	public Label toLabelForUpdate() {
		return Label.builder()
			.id(id)
			.title(title)
			.description(description)
			.color(color)
			.build();
	}
}
