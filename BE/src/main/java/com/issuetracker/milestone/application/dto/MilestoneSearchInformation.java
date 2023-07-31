package com.issuetracker.milestone.application.dto;

import com.issuetracker.milestone.domain.Milestone;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MilestoneSearchInformation {

	private String title;

	public static MilestoneSearchInformation from(Milestone milestone) {
		return new MilestoneSearchInformation(
			milestone.getTitle()
		);
	}
}
