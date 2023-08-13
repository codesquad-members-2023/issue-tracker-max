package com.issuetrackermax.service.comment;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.issuetrackermax.domain.comment.CommentRepository;
import com.issuetrackermax.domain.comment.entity.Comment;
import com.issuetrackermax.domain.member.MemberValidator;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CommentService {
	private final MemberValidator memberValidator;
	private final CommentRepository commentRepository;

	//todo : 수정 필요
	@Transactional
	public Long save(Comment comment) {
		memberValidator.existById(comment.getWriterId());
		return commentRepository.save(comment);
	}

	public List<Comment> findByIssueId(Long id) {
		return commentRepository.findByIssueId(id);
	}
}
