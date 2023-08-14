package com.issuetrackermax.domain.comment;

import org.springframework.stereotype.Component;

import com.issuetrackermax.common.exception.ApiException;
import com.issuetrackermax.common.exception.domain.CommentException;
import com.issuetrackermax.domain.comment.entity.Comment;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class CommentValidator {
	private final CommentRepository commentRepository;

	public void checkWriter(Long commentId, Long memberId) {
		Comment comment = commentRepository.findById(commentId);
		if (!comment.getWriterId().equals(memberId)) {
			throw new ApiException(CommentException.NOT_AUTHORIZED);
		}
	}
}
