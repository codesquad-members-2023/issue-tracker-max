package com.issuetracker.issue.domain;

public interface IssueCommentRepository {

	long save(IssueComment issueComment);

	int updateContent(Long issueCommentId, String content);
}
