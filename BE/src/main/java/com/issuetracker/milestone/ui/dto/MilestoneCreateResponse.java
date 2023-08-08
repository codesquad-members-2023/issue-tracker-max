package com.issuetracker.milestone.ui.dto;

import com.issuetracker.milestone.application.dto.MilestoneCreateInformation;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class MilestoneCreateResponse {

	private Long id;

	public static MilestoneCreateResponse from(MilestoneCreateInformation milestoneCreateInformation) {
		return new MilestoneCreateResponse(
			milestoneCreateInformation.getId()
		);
	}
}
