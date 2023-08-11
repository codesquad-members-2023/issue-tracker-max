package org.presents.issuetracker.label.dto.request;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class LabelCreateRequest {
	@NotBlank
	private String name;

	@NotBlank
	private String backgroundColor;

	@NotBlank
	private String textColor;

	private String description;

	@Builder
	public LabelCreateRequest(String name, String description, String backgroundColor, String textColor) {
		this.name = name;
		this.description = description;
		this.backgroundColor = backgroundColor;
		this.textColor = textColor;
	}
}
