package com.issuetracker.issue.domain;

public interface IssueRepository {

	Long save(Issue issue);

	IssuesCountData findAllCount();
}
