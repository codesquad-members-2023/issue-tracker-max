package com.issuetracker.issue.application.dto.assignedlabel;

import java.util.List;
import java.util.stream.Collectors;

import com.issuetracker.label.application.dto.LabelInformation;
import com.issuetracker.label.domain.Label;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AssignedLabelCandidatesInformation {

	private List<LabelInformation> assignedLabels;
	private List<LabelInformation> labels;

	public static AssignedLabelCandidatesInformation from(List<Label> assignedLabels, List<Label> labels) {
		return new AssignedLabelCandidatesInformation(
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
			.map(AssignedLabelCandidatesInformation::from)
			.collect(Collectors.toUnmodifiableList());
	}
}
