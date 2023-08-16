package com.issuetracker.issue.domain;

import java.util.List;

public interface IssueRepository {

	Long save(Issue issue);

	IssuesCountData findAllCount();

	int updateOpen(boolean isOpen, long id);

	int updateAllOpen(boolean isOpen, List<Long> ids);

	int updateTitle(long id, String title);

	int updateContent(long id, String content);

	int updateMilestone(long id, Long milestoneId);

	int delete(long id);

	boolean existById(long id);
}

