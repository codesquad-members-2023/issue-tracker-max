package org.presents.issuetracker.issue.dto.response;

import java.util.List;
import java.util.stream.Collectors;

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

	public static List<LabelSearch> from(List<Label> labels) {
		return labels.stream().map(LabelSearch::fromEntity).collect(Collectors.toUnmodifiableList());
	}
}
