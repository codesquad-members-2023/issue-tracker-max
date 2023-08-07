package codesquard.app.comment.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import codesquard.app.api.errors.exception.NoSuchCommentException;
import codesquard.app.api.errors.exception.NoSuchIssueException;
import codesquard.app.comment.entity.Comment;
import codesquard.app.comment.repository.CommentRepository;
import codesquard.app.comment.service.request.CommentModifyServiceRequest;
import codesquard.app.comment.service.request.CommentSaveServiceRequest;
import codesquard.app.comment.service.response.CommentDeleteResponse;
import codesquard.app.comment.service.response.CommentModifyResponse;
import codesquard.app.comment.service.response.CommentSaveResponse;
import codesquard.app.issue.repository.IssueRepository;
import lombok.RequiredArgsConstructor;

@Transactional
@RequiredArgsConstructor
@Service
public class CommentService {

	private final CommentRepository commentRepository;
	private final IssueRepository issueRepository;

	public CommentSaveResponse save(CommentSaveServiceRequest serviceRequest, LocalDateTime createdAt) {
		validateIssueId(serviceRequest.getIssueId());
		Comment comment = serviceRequest.toEntity(createdAt);
		Long savedCommentId = commentRepository.save(comment);

		return new CommentSaveResponse(savedCommentId);
	}

	public CommentModifyResponse modify(CommentModifyServiceRequest serviceRequest, LocalDateTime modifiedAt) {
		validateCommentId(serviceRequest.getId());
		Comment comment = serviceRequest.toEntity(modifiedAt);
		Long modifiedCommentId = commentRepository.modify(comment);

		return new CommentModifyResponse(modifiedCommentId);
	}

	public CommentDeleteResponse delete(Long id) {
		validateCommentId(id);
		Long deletedCommentId = commentRepository.deleteById(id);

		return new CommentDeleteResponse(deletedCommentId);
	}

	private void validateIssueId(Long issueId) {
		if (!issueRepository.exist(issueId)) {
			throw new NoSuchIssueException();
		}
	}

	private void validateCommentId(Long commentId) {
		if (!commentRepository.isExists(commentId)) {
			throw new NoSuchCommentException();
		}
	}

}
