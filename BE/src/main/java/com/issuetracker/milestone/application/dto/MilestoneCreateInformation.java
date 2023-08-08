package com.issuetracker.milestone.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MilestoneCreateInformation {

	private Long id;

	public static MilestoneCreateInformation from(Long id) {
		return new MilestoneCreateInformation(id);
	}
}
