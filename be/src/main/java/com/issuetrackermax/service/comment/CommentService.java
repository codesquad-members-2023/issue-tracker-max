package com.issuetrackermax.service.comment;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.issuetrackermax.controller.comment.dto.request.CommentCreateRequest;
import com.issuetrackermax.controller.comment.dto.request.CommentModifyRequest;
import com.issuetrackermax.controller.comment.dto.response.CommentResponse;
import com.issuetrackermax.domain.comment.CommentRepository;
import com.issuetrackermax.domain.comment.CommentValidator;
import com.issuetrackermax.domain.comment.entity.Comment;
import com.issuetrackermax.domain.member.MemberValidator;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CommentService {
	private final MemberValidator memberValidator;
	private final CommentValidator commentValidator;
	private final CommentRepository commentRepository;

	@Transactional(readOnly = true)
	public List<Comment> findByIssueId(Long id) {
		return commentRepository.findByIssueId(id);
	}

	@Transactional
	public void modifyComment(CommentModifyRequest commentModifyRequest, Long commentId, Long memberId) {
		memberValidator.existById(memberId);
		commentValidator.checkWriter(commentId, memberId);
		commentRepository.updateComment(Comment.from(commentModifyRequest), commentId);
	}

	@Transactional
	public CommentResponse save(CommentCreateRequest commentCreateRequest, Long memberId) {
		memberValidator.existById(memberId);
		Long commentId = commentRepository.save(Comment.from(commentCreateRequest, memberId));
		return CommentResponse.from(commentRepository.findById(commentId).get());
	}

	@Transactional
	public void delete(Long commentId, Long memeberId) {
		memberValidator.existById(memeberId);
		commentValidator.checkWriter(commentId, memeberId);
		commentRepository.deleteById(commentId);
	}
}
