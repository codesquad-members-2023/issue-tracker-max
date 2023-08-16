package com.issuetrackermax.domain.issue;

import lombok.Getter;

@Getter
public enum IssueStatus {
	OPEN_ISSUE("open"), CLOSED_ISSUE("closed");
	String status;

	IssueStatus(String status) {
		this.status = status;
	}
}
