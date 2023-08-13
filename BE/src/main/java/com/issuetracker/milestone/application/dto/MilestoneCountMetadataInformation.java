package com.issuetracker.milestone.application.dto;

import com.issuetracker.milestone.domain.MilestoneCountMetadata;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MilestoneCountMetadataInformation {

	private int totalLabelCount;
	private int totalMilestoneCount;
	private int openMilestoneCount;
	private int closeMilestoneCount;

	public static MilestoneCountMetadataInformation from(MilestoneCountMetadata metadata) {
		return new MilestoneCountMetadataInformation(
			metadata.getTotalLabelCount(),
			metadata.getTotalMilestoneCount(),
			metadata.getOpenMilestoneCount(),
			metadata.getCloseMilestoneCount()
		);
	}
}
