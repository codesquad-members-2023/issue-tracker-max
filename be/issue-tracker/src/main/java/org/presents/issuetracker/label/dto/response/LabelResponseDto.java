package org.presents.issuetracker.label.dto.response;

import org.presents.issuetracker.label.entity.Label;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LabelResponseDto {
	private Long labelId;
	private String name;
	private String textColor;
	private String backgroundColor;

	public static LabelResponseDto fromEntity(Label label) {
		return LabelResponseDto.builder()
			.labelId(label.getLabelId())
			.name(label.getName())
			.backgroundColor(label.getBackgroundColor())
			.textColor(label.getTextColor())
			.build();
	}
}
