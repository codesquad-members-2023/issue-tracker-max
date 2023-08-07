package com.issuetracker.label.application.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.issuetracker.label.domain.Label;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LabelInformation {

	private Long id;
	private String title;
	private String color;
	private String description;

	public static LabelInformation from(Label label) {
		return new LabelInformation(
			label.getId(),
			label.getTitle(),
			label.getColor(),
			label.getDescription()
		);
	}

	public static List<LabelInformation> from(List<Label> labels) {
		return labels.stream()
			.map(LabelInformation::from)
			.collect(Collectors.toUnmodifiableList());
	}
}
