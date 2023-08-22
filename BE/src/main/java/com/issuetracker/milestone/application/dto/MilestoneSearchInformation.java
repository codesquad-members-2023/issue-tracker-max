package com.issuetracker.milestone.application.dto;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.issuetracker.milestone.domain.Milestone;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MilestoneSearchInformation {

	private Long id;
	private String title;
	private int progress;

	public static MilestoneSearchInformation from(Milestone milestone) {
		if (Objects.isNull(milestone)) {
			return null;
		}

		return new MilestoneSearchInformation(
			milestone.getId(),
			milestone.getTitle(),
			milestone.getProgress()
		);
	}

	public static List<MilestoneSearchInformation> from(List<Milestone> milestones) {
		return milestones.stream()
			.map(MilestoneSearchInformation::from)
			.collect(Collectors.toUnmodifiableList());
	}
}
