package com.issuetracker.issue.application.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class IssueUpdateAllOpenData {

	private List<Long> ids;
	private boolean isOpen;

	public boolean getIsOpen() {
		return isOpen;
	}
}
