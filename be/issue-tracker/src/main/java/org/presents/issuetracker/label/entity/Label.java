package org.presents.issuetracker.label.entity;

import java.util.Objects;

import org.presents.issuetracker.label.dto.request.LabelUpdateRequestDto;

import lombok.Getter;
import lombok.Setter;

@Getter
public class Label {
	private Long labelId;
	private String name;
	private String description;
	private String backgroundColor;
	private String textColor;
	private boolean isDeleted = false;

	public Label(Long labelId, String name, String description, String backgroundColor, String textColor) {
		this.labelId = labelId;
		this.name = name;
		this.description = description;
		this.backgroundColor = backgroundColor;
		this.textColor = textColor;
	}

	public Label(String name, String description, String backgroundColor, String textColor) {
		this.name = name;
		this.description = description;
		this.backgroundColor = backgroundColor;
		this.textColor = textColor;
	}

	public static Label of(Long labelId, String name, String description, String backgroundColor, String textColor) {
		return new Label(labelId, name, description, backgroundColor, textColor);
	}

	public static Label of(String name, String description, String backgroundColor, String textColor) {
		return new Label(name, description, backgroundColor, textColor);
	}

	public Label updateFrom(LabelUpdateRequestDto dto) {
		if (!Objects.equals(this.name, dto.getName())) {
			this.name = dto.getName();
		}
		if (!Objects.equals(this.description, dto.getDescription())) {
			this.description = dto.getDescription();
		}
		if (!Objects.equals(this.backgroundColor, dto.getBackgroundColor())) {
			this.backgroundColor = dto.getBackgroundColor();
		}
		if (!Objects.equals(this.textColor, dto.getTextColor())) {
			this.textColor = dto.getTextColor();
		}

		return this;
	}
}
