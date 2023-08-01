package codesquard.app.comment.controller;

import java.time.LocalDateTime;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import codesquard.app.comment.controller.request.CommentSaveRequest;
import codesquard.app.comment.service.CommentService;
import codesquard.app.comment.service.response.CommentResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class CommentController {

	private final CommentService commentService;

	@PostMapping("/api/comments")
	public ResponseEntity<CommentResponse> saveComment(@Valid @RequestBody CommentSaveRequest request) {
		LocalDateTime createdAt = LocalDateTime.now();
		return ResponseEntity.status(HttpStatus.CREATED)
			.body(commentService.save(request.toServiceRequest(), createdAt));
	}

}
