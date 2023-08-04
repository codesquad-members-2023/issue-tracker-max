package org.presents.issuetracker.issue.dto.response;

import org.presents.issuetracker.label.entity.Label;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LabelSearch {
	private Long id;
	private String name;
	private String textColor;
	private String backgroundColor;

	public static LabelSearch fromEntity(Label label) {
		return LabelSearch.builder()
			.id(label.getId())
			.name(label.getName())
			.backgroundColor(label.getBackgroundColor())
			.textColor(label.getTextColor())
			.build();
	}
}
