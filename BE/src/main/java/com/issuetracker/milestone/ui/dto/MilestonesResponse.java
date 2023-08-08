package com.issuetracker.milestone.ui.dto;

import java.util.List;

import com.issuetracker.milestone.application.dto.MilestoneInformation;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class MilestonesResponse {

	private List<MilestoneResponse> milestones;

	public static MilestonesResponse from(List<MilestoneInformation> milestoneInformations) {
		return new MilestonesResponse(MilestoneResponse.from(milestoneInformations));
	}
}

