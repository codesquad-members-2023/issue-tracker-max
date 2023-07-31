package com.issuetrackermax.domain.label.entity;

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
}
