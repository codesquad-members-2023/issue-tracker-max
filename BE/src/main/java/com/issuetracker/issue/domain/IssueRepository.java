package com.issuetracker.issue.domain;

public interface IssueRepository {

	Long save(Issue issue);

	IssuesCountData findAllCount();

	int updateOpen(long id, boolean isOpen);

	int updateTitle(long id, String title);

	int updateContent(long id, String content);

	int delete(long id);

	boolean existById(long id);
}
