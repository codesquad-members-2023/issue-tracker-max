package com.issuetracker.milestone.application.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MilestonesInformation {

	private MilestoneCountMetadataInformation metadata;
	private List<MilestoneInformation> milestones;

	public static MilestonesInformation from(MilestoneCountMetadataInformation metadata,
		List<MilestoneInformation> milestones) {
		return new MilestonesInformation(
			metadata,
			milestones
		);
	}
}
