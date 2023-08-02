package org.presents.issuetracker.label.entity;

import lombok.Getter;

@Getter
public class Label {
	private Long labelId;
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

	public static Label of(String name, String description, String backgroundColor, String textColor) {
		return new Label(name, description, backgroundColor, textColor);
	}
}
