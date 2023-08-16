package com.issuetrackermax.controller.label.dto.request;

import javax.validation.constraints.NotBlank;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LabelModifyRequest {
	@NotBlank(message = "이름을 입력해주세요.")
	private String name;
	private String description;
	@NotBlank(message = "텍스트 색상을 선택해주세요.")
	private String textColor;
	@NotBlank(message = "배경 색상을 선택해주세요.")
	private String backgroundColor;

	@Builder
	public LabelModifyRequest(String name, String description, String textColor, String backgroundColor) {
		this.name = name;
		this.description = description;
		this.textColor = textColor;
		this.backgroundColor = backgroundColor;
	}
}
