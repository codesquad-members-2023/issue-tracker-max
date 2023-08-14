package com.issuetrackermax.controller.label.dto.request;

import javax.validation.constraints.NotBlank;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LabelPostRequest {
	@NotBlank(message = "이름을 입력해주세요.")
	private String title;
	private String description;
	@NotBlank(message = "텍스트 색상을 선택해주세요.")
	private String textColor;
	@NotBlank(message = "배경 색상을 선택해주세요.")
	private String backgroundColor;

	@Builder
	public LabelPostRequest(String title, String description, String textColor, String backgroundColor) {
		this.title = title;
		this.description = description;
		this.textColor = textColor;
		this.backgroundColor = backgroundColor;
	}
}
