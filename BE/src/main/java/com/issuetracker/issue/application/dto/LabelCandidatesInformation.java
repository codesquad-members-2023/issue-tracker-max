package com.issuetracker.issue.application.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.issuetracker.label.application.dto.LabelInformation;
import com.issuetracker.label.domain.Label;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LabelCandidatesInformation {

	private List<LabelInformation> assignedLabels;
	private List<LabelInformation> labels;

	public static LabelCandidatesInformation from(List<Label> assignedLabels, List<Label> labels) {
		return new LabelCandidatesInformation(
			from(assignedLabels),
			from(labels)
		);
	}

	private static LabelInformation from(Label label) {
		return new LabelInformation(
			label.getId(),
			label.getTitle(),
			label.getColor(),
			label.getDescription()
		);
	}

	private static List<LabelInformation> from(List<Label> labels) {
		return labels.stream()
			.map(LabelCandidatesInformation::from)
			.collect(Collectors.toUnmodifiableList());
	}
}
