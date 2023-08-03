package codesquard.app.comment.controller;

import java.time.LocalDateTime;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import codesquard.app.comment.controller.request.CommentModifyRequest;
import codesquard.app.comment.controller.request.CommentSaveRequest;
import codesquard.app.comment.service.CommentService;
import codesquard.app.comment.service.response.CommentDeleteResponse;
import codesquard.app.comment.service.response.CommentModifyResponse;
import codesquard.app.comment.service.response.CommentSaveResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class CommentController {

	private final CommentService commentService;

	@PostMapping("/api/comments")
	public ResponseEntity<CommentSaveResponse> saveComment(@Valid @RequestBody CommentSaveRequest request) {
		LocalDateTime createdAt = LocalDateTime.now();
		return ResponseEntity.status(HttpStatus.CREATED)
			.body(commentService.save(request.toServiceRequest(), createdAt));
	}

	@PatchMapping("/api/comments/{id}")
	public ResponseEntity<CommentModifyResponse> modifyComment(@Valid @RequestBody CommentModifyRequest request,
		@PathVariable Long id) {
		LocalDateTime modifiedAt = LocalDateTime.now();
		return ResponseEntity.status(HttpStatus.OK)
			.body(commentService.modify(request.toServiceRequest(id), modifiedAt));
	}

	@DeleteMapping("/api/comments/{id}")
	public ResponseEntity<CommentDeleteResponse> deleteComment(@PathVariable Long id) {
		return ResponseEntity.status(HttpStatus.OK)
			.body(commentService.delete(id));
	}

}
