package com.issuetracker.issue.ui.dto;

import com.issuetracker.issue.application.dto.IssueCreateInformation;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class IssueCreateResponse {

	private Long id;

	public static IssueCreateResponse from(IssueCreateInformation issueCreateInformation) {
		return new IssueCreateResponse(
			issueCreateInformation.getId()
		);
	}
}
