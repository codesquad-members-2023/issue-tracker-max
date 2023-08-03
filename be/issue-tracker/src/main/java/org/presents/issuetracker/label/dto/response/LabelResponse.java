package org.presents.issuetracker.label.dto.response;

import org.presents.issuetracker.label.entity.Label;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LabelResponse {
	private Long labelId;
	private String name;
	private String textColor;
	private String backgroundColor;

	public static LabelResponse fromEntity(Label label) {
		return LabelResponse.builder()
			.labelId(label.getId())
			.name(label.getName())
			.backgroundColor(label.getBackgroundColor())
			.textColor(label.getTextColor())
			.build();
	}
}
