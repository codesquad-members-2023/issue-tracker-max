package org.presents.issuetracker.label.entity;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Label {
	private Long id;
	private String name;
	private String description;
	private String backgroundColor;
	private String textColor;
	private boolean isDeleted = false;

	private Label(String name, String description, String backgroundColor, String textColor) {
		this.name = name;
		this.description = description;
		this.backgroundColor = backgroundColor;
		this.textColor = textColor;
	}

	public Label(Long id, String name, String description, String backgroundColor, String textColor) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.backgroundColor = backgroundColor;
		this.textColor = textColor;
	}

	public static Label of(String name, String description, String backgroundColor, String textColor) {
		return new Label(name, description, backgroundColor, textColor);
	}
}
