package com.issuetracker.issue.application.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.issuetracker.label.domain.Label;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class IssueLabelMappingInformation {

	private Long id;
	private String title;
	private String color;

	public static IssueLabelMappingInformation from(Label label) {
		return new IssueLabelMappingInformation(
			label.getId(),
			label.getTitle(),
			label.getColor()
		);
	}

	public static List<IssueLabelMappingInformation> from(List<Label> labels) {
		return labels.stream()
			.map(IssueLabelMappingInformation::from)
			.collect(Collectors.toUnmodifiableList());
	}
}
