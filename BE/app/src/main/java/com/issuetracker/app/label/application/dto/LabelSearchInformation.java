package com.issuetracker.app.label.application.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.issuetracker.app.label.domain.Label;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LabelSearchInformation {

	private String title;
	private String color;

	public static LabelSearchInformation from(Label label) {
		return new LabelSearchInformation(
			label.getTitle(),
			label.getColor()
		);
	}

	public static List<LabelSearchInformation> from(List<Label> labels) {
		return labels.stream()
			.map(LabelSearchInformation::from)
			.collect(Collectors.toUnmodifiableList());
	}
}
