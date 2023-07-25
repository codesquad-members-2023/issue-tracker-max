package kr.codesquad.issuetracker.domain;

import lombok.Getter;

@Getter
public class IssueLabel {

	private Integer id;
	private Integer issueId;
	private Integer labelId;
}
