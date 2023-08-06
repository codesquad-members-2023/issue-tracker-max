package org.presents.issuetracker.label.dto.request;

import lombok.Getter;

@Getter
public class LabelUpdateRequestDto {
	private Long id;
	private String name;
	private String description;
	private String backgroundColor;
	private String textColor;
}
