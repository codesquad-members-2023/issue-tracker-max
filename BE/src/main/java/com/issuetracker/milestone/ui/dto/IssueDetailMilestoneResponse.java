package com.issuetracker.milestone.ui.dto;

import com.issuetracker.milestone.application.dto.IssueDetailMilestoneInformation;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class IssueDetailMilestoneResponse {

	private Long id;
	private String title;
	private Double progress;

	public static IssueDetailMilestoneResponse from(IssueDetailMilestoneInformation milestone) {
		return new IssueDetailMilestoneResponse(
			milestone.getId(),
			milestone.getTitle(),
			milestone.getProgress()
		);
	}
}
