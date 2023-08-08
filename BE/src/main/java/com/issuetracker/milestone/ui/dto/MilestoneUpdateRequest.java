package com.issuetracker.milestone.ui.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import com.issuetracker.milestone.application.dto.MilestoneUpdateInputData;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class MilestoneUpdateRequest {

	@NotBlank(message = "제목은 필수 값 입니다.")
	@Length(max = 50, message = "제목은 최대 50글자 입니다.")
	private String title;

	@Length(max = 100, message = "설명은 최대 100글자 입니다.")
	private String description;

	@Pattern(regexp = "\\d{4}\\.\\d{2}\\.\\d{2}", message = "데드라인은 YYYY.MM.DD 형식으로 입력해 주세요.")
	private String deadline;

	public MilestoneUpdateInputData toMilestoneUpdateInputData(Long id) {
		return new MilestoneUpdateInputData(
			id,
			title,
			description,
			deadline
		);
	}
}
