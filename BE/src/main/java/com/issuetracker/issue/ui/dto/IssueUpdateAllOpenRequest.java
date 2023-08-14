package com.issuetracker.issue.ui.dto;

import java.util.List;

import com.issuetracker.common.util.ConvertorUtil;
import com.issuetracker.issue.application.dto.IssueUpdateAllOpenData;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class IssueUpdateAllOpenRequest {

	private List<Long> ids;
	private boolean isOpen;

	public IssueUpdateAllOpenData toIssueUpdateAllOpenData() {
		return new IssueUpdateAllOpenData(
			ConvertorUtil.converterToNonNullList(ids),
			isOpen
		);
	}

	public boolean getIsOpen() {
		return isOpen;
	}
}
