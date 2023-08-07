package org.presents.issuetracker.label.dto.response;

import org.presents.issuetracker.label.entity.Label;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LabelPreviewResponse {
	private long id;
	private String name;
	private String textColor;
	private String backgroundColor;

	@Builder
	private LabelPreviewResponse(long id, String name, String textColor, String backgroundColor) {
		this.id = id;
		this.name = name;
		this.textColor = textColor;
		this.backgroundColor = backgroundColor;
	}

	public static LabelPreviewResponse from(Label label) {
		return LabelPreviewResponse.builder()
			.id(label.getId())
			.name(label.getName())
			.backgroundColor(label.getBackgroundColor())
			.textColor(label.getTextColor())
			.build();
	}
}
