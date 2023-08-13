package com.issuetracker.milestone.ui.dto;

import com.issuetracker.milestone.application.dto.MilestoneCountMetadataInformation;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class MilestoneCountMetadataResponse {

	private int totalLabelCount;
	private int totalMilestoneCount;
	private int openMilestoneCount;
	private int closeMilestoneCount;

	public static MilestoneCountMetadataResponse from(
		MilestoneCountMetadataInformation metadataInformation) {
		return new MilestoneCountMetadataResponse(
			metadataInformation.getTotalLabelCount(),
			metadataInformation.getTotalMilestoneCount(),
			metadataInformation.getOpenMilestoneCount(),
			metadataInformation.getCloseMilestoneCount()
		);
	}
}
