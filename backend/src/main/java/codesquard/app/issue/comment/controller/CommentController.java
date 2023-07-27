package codesquard.app.issue.comment.controller;

import org.springframework.web.bind.annotation.RestController;

import codesquard.app.issue.comment.service.CommentService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class CommentController {

	private final CommentService commentService;
}
