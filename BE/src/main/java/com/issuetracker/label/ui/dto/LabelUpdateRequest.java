package com.issuetracker.label.ui.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import com.issuetracker.label.application.dto.LabelUpdateInputData;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class LabelUpdateRequest {

	@NotBlank(message = "제목은 필수 값 입니다.")
	@Length(max = 50, message = "제목은 최대 50글자 입니다.")
	private String title;

	@Length(max = 100, message = "설명은 최대 100글자 입니다.")
	private String description;

	@NotBlank(message = "컬러는 필수 값 입니다.")
	@Pattern(regexp = "^#[0-9A-Fa-f]{6}$", message = "올바른 컬러 형식이 아닙니다.")
	private String color;

	public LabelUpdateInputData toLabelUpdateInputData(Long id) {
		return new LabelUpdateInputData(
			id,
			title,
			description,
			color
		);
	}
}
