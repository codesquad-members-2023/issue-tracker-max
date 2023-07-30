package com.issuetracker.app.milestone.application.dto;

import com.issuetracker.app.milestone.domain.Milestone;

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
