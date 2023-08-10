package com.issuetracker.milestone.application.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.issuetracker.milestone.domain.Milestone;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MilestoneCandidatesInformation {

	private List<MilestoneInformation> assignedMilestones;
	private List<MilestoneInformation> milestones;

	public static MilestoneCandidatesInformation from(
		List<Milestone> assignedMilestones, List<Milestone> milestones) {
		return new MilestoneCandidatesInformation(
			from(assignedMilestones),
			from(milestones)
		);
	}

	private static MilestoneInformation from(Milestone milestone) {
		return new MilestoneInformation(
			milestone.getId(),
			milestone.getTitle(),
			milestone.getDescription(),
			milestone.getDeadline(),
			milestone.getIsOpen(),
			milestone.getProgress()
		);
	}

	private static List<MilestoneInformation> from(List<Milestone> milestones) {
		return milestones.stream()
			.map(MilestoneCandidatesInformation::from)
			.collect(Collectors.toUnmodifiableList());
	}
}


