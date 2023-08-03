package com.issuetracker.issue.application.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.issuetracker.issue.domain.Milestone;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MilestoneSearchInformation {

	private Long id;
	private String title;

	public static MilestoneSearchInformation from(Milestone milestone) {
		return new MilestoneSearchInformation(
			milestone.getId(),
			milestone.getTitle()
		);
	}

	public static List<MilestoneSearchInformation> from(List<Milestone> milestones) {
		return milestones.stream()
			.map(MilestoneSearchInformation::from)
			.collect(Collectors.toUnmodifiableList());
	}
}
