package codesquard.app.comment.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import codesquard.app.comment.entity.Comment;
import codesquard.app.comment.repository.CommentRepository;
import codesquard.app.comment.service.request.CommentSaveServiceRequest;
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

}
