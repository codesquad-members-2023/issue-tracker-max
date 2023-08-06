package codesquad.issueTracker.comment.controller;

import org.springframework.web.bind.annotation.RestController;

import codesquad.issueTracker.comment.service.CommentService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class CommentController {
	private final CommentService commentService;
}
