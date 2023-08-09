package com.issuetracker.issue.domain.comment;

public interface IssueCommentRepository {

	long save(IssueComment issueComment);

	int updateContent(Long issueCommentId, String content);
}
