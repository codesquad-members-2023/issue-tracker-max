package com.issuetrackermax.service.comment;

import org.springframework.stereotype.Service;

import com.issuetrackermax.domain.comment.CommentRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CommentService {
	private final CommentRepository commentRepository;
}
