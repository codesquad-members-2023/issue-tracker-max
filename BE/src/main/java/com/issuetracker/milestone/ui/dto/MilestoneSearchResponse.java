package com.issuetracker.milestone.ui.dto;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.issuetracker.milestone.application.dto.MilestoneSearchInformation;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class MilestoneSearchResponse {

	private Long id;
	private String title;

	public static MilestoneSearchResponse from(MilestoneSearchInformation milestone) {
		if (Objects.isNull(milestone)) {
			return null;
		}

		return new MilestoneSearchResponse(
			milestone.getId(),
			milestone.getTitle()
		);
	}

	public static List<MilestoneSearchResponse> from(List<MilestoneSearchInformation> milestoneSearchResponses) {
		return milestoneSearchResponses.stream()
			.map(MilestoneSearchResponse::from)
			.collect(Collectors.toUnmodifiableList());
	}

}
