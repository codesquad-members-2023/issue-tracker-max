package com.issuetrackermax.service.comment;

import java.util.List;

import org.springframework.stereotype.Service;

import com.issuetrackermax.domain.comment.CommentRepository;
import com.issuetrackermax.domain.comment.entity.Comment;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CommentService {
	private final CommentRepository commentRepository;

	//todo : 수정 필요
	public Long save(Comment comment) {
		return commentRepository.save(comment);
	}

	public List<Comment> findByIssueId(Long id) {
		return commentRepository.findByIssueId(id);
	}
}
