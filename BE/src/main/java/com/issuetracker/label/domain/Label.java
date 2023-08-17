package com.issuetracker.label.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
public class Label {

	private Long id;
	private String title;
	private String description;
	private String backgroundColor;
	private String textColor;

	@Builder
	public Label(Long id, String title, String description, String backgroundColor, String textColor) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.backgroundColor = backgroundColor;
		this.textColor = textColor;
	}

	public boolean equalsId(Long id) {
		return this.id == id;
	}
}
