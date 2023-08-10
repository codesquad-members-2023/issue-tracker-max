package com.issuetracker.milestone.ui.dto;

import java.util.List;

import com.issuetracker.milestone.application.dto.MilestonesInformation;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class MilestonesResponse {

	private MilestoneCountMetadataResponse metadata;
	private List<MilestoneResponse> milestones;

	public static MilestonesResponse from(MilestonesInformation milestonesInformation) {
		return new MilestonesResponse(
			MilestoneCountMetadataResponse.from(milestonesInformation.getMetadata()),
			MilestoneResponse.from(milestonesInformation.getMilestones())
		);
	}
}

