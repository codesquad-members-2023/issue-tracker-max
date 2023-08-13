package com.issuetracker.issue.ui.dto.milestone;

import java.util.List;

import com.issuetracker.milestone.application.dto.MilestoneCandidatesInformation;
import com.issuetracker.milestone.ui.dto.MilestoneResponse;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class MilestoneCandidatesResponse {

	private List<MilestoneResponse> assignedMilestones;
	private List<MilestoneResponse> milestones;

	public static MilestoneCandidatesResponse from(
		MilestoneCandidatesInformation milestoneCandidatesInformation) {

		return new MilestoneCandidatesResponse(
			MilestoneResponse.from(milestoneCandidatesInformation.getAssignedMilestones()),
			MilestoneResponse.from(milestoneCandidatesInformation.getMilestones())
		);
	}
}
