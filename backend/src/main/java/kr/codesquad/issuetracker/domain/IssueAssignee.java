package kr.codesquad.issuetracker.domain;

import lombok.Getter;

@Getter
public class IssueAssignee {

	private Integer id;
	private Integer issueId;
	private Integer userAccountId;
}
