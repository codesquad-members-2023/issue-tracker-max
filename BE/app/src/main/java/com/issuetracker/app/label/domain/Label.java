package com.issuetracker.app.label.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Label {

	private Long id;
	private String title;
	private String description;
	private String color;

	@Builder(builderClassName = "LabelBuilder", access = AccessLevel.PRIVATE)
	private Label(Long id, String title, String description, String color) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.color = color;
	}

	@Builder(builderClassName = "LabelRequiredBuilder", access = AccessLevel.PRIVATE)
	public static LabelBuilder labelBuilder(String title, String color) {
		return new LabelBuilder()
			.title(title)
			.color(color);
	}
}
