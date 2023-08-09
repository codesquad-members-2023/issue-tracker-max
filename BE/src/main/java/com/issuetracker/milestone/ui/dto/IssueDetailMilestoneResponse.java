package com.issuetracker.milestone.ui.dto;

import java.util.Objects;

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
		if (Objects.isNull(milestone)) {
			return null;
		}

		return new IssueDetailMilestoneResponse(
			milestone.getId(),
			milestone.getTitle(),
			milestone.getProgress()
		);
	}
}
