package com.issuetracker.issue.application;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.issuetracker.issue.application.dto.comment.IssueCommentCreateData;
import com.issuetracker.issue.application.dto.comment.IssueCommentCreateInformation;
import com.issuetracker.issue.application.dto.comment.IssueCommentUpdateData;
import com.issuetracker.issue.domain.comment.IssueCommentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class IssueCommentService {

	private final IssueValidator issueValidator;
	private final IssueCommentRepository issueCommentRepository;

	@Transactional
	public IssueCommentCreateInformation createIssueComment(IssueCommentCreateData issueCommentCreateData) {
		issueValidator.verifyCreateIssueComment(issueCommentCreateData.getIssueId(),
			issueCommentCreateData.getAuthorId());
		Long savedId = issueCommentRepository.save(issueCommentCreateData.toIssueComment(LocalDateTime.now()));
		return IssueCommentCreateInformation.from(savedId);
	}

	@Transactional
	public void updateIssueCommentContent(IssueCommentUpdateData issueCommentUpdateData) {
		issueValidator.verifyNonNull(issueCommentUpdateData.getContent());
		int updatedCount = issueCommentRepository.updateContent(issueCommentUpdateData.getIssueCommentId(),
			issueCommentUpdateData.getContent());
		issueValidator.verifyCommentUpdatedOrDeletedCount(updatedCount);
	}
}
