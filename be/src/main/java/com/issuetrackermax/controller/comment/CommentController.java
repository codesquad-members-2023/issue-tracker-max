package com.issuetrackermax.controller.comment;

import org.springframework.web.bind.annotation.RestController;

import com.issuetrackermax.service.comment.CommentService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class CommentController {
	private final CommentService commentService;
}
