package codesquard.app.comment.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import codesquard.app.comment.entity.Comment;
import codesquard.app.comment.repository.CommentRepository;
import codesquard.app.comment.service.request.CommentModifyServiceRequest;
import codesquard.app.comment.service.request.CommentSaveServiceRequest;
import codesquard.app.comment.service.response.CommentDeleteResponse;
import codesquard.app.comment.service.response.CommentModifyResponse;
import codesquard.app.comment.service.response.CommentSaveResponse;
import lombok.RequiredArgsConstructor;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class CommentService {

	private final CommentRepository commentRepository;

	@Transactional
	public CommentSaveResponse save(CommentSaveServiceRequest serviceRequest, LocalDateTime createdAt) {
		Comment comment = serviceRequest.toEntity(createdAt);
		Long savedCommentId = commentRepository.save(comment);

		return new CommentSaveResponse(true, savedCommentId);
	}

	@Transactional
	public CommentModifyResponse modify(CommentModifyServiceRequest serviceRequest, LocalDateTime modifiedAt) {
		Comment comment = serviceRequest.toEntity(modifiedAt);
		Long modifiedCommentId = commentRepository.modify(comment);

		return new CommentModifyResponse(true, modifiedCommentId);
	}

	@Transactional
	public CommentDeleteResponse delete(Long id) {
		commentRepository.deleteById(id);

		return new CommentDeleteResponse(true);
	}

}
