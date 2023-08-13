package com.issuetracker.milestone.application.dto;

import java.util.Objects;

import com.issuetracker.milestone.domain.Milestone;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class IssueDetailMilestoneInformation {

	private Long id;
	private String title;
	private Integer progress;

	public static IssueDetailMilestoneInformation from(Milestone milestone) {
		if (Objects.isNull(milestone)) {
			return null;
		}

		return new IssueDetailMilestoneInformation(
			milestone.getId(),
			milestone.getTitle(),
			milestone.getProgress()
		);
	}
}
