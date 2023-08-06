package com.issuetracker.label.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Label {

	private Long id;
	private String title;
	private String description;
	private String color;

	@Builder(builderClassName = "LabelBuilder")
	private Label(Long id, String title, String description, String color) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.color = color;
	}

	public static Label createInstanceById(Long id) {
		return Label.builder()
			.id(id)
			.build();
	}
}
