package com.issuetracker.issue.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class IssueCreateInformation {

	private Long id;

	public static IssueCreateInformation from(Long id) {
		return new IssueCreateInformation(id);
	}
}
