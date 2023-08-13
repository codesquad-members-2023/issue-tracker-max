package com.issuetracker.milestone.application.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import com.issuetracker.milestone.domain.Milestone;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MilestoneInformation {

	private Long id;
	private String title;
	private String description;
	private LocalDate deadline;
	private boolean isOpen;
	private Integer progress;
	private Integer openIssueCount;
	private Integer closeIssueCount;

	public static MilestoneInformation from(Milestone milestone) {
		return new MilestoneInformation(
			milestone.getId(),
			milestone.getTitle(),
			milestone.getDescription(),
			milestone.getDeadline(),
			milestone.getIsOpen(),
			milestone.getProgress(),
			milestone.getOpenIssueCount(),
			milestone.getCloseIssueCount()
		);
	}

	public static List<MilestoneInformation> from(List<Milestone> milestones) {
		return milestones.stream()
			.map(MilestoneInformation::from)
			.collect(Collectors.toUnmodifiableList());
	}
}
