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
	private boolean isDeleted;

	@Builder
	private Label(Long id, String title, String description, String color, boolean isDeleted) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.color = color;
		this.isDeleted = isDeleted;
	}

	public static Label createInstanceById(Long id) {
		return Label.builder()
			.id(id)
			.build();
	}
}
