package codesquard.app.comment.controller;

import java.time.LocalDateTime;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import codesquard.app.api.response.ApiResponse;
import codesquard.app.api.response.ResponseMessage;
import codesquard.app.comment.controller.request.CommentModifyRequest;
import codesquard.app.comment.controller.request.CommentSaveRequest;
import codesquard.app.comment.service.CommentService;
import codesquard.app.comment.service.response.CommentDeleteResponse;
import codesquard.app.comment.service.response.CommentModifyResponse;
import codesquard.app.comment.service.response.CommentSaveResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping(path = "/api/comments")
@RestController
public class CommentController {

	private final CommentService commentService;

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public ApiResponse<CommentSaveResponse> saveComment(@Valid @RequestBody CommentSaveRequest request) {
		LocalDateTime createdAt = LocalDateTime.now();
		return ApiResponse.of(HttpStatus.CREATED, ResponseMessage.COMMENT_SAVE_SUCCESS,
			commentService.save(request.toServiceRequest(), createdAt));
	}

	@PatchMapping("/{id}")
	public ApiResponse<CommentModifyResponse> modifyComment(@Valid @RequestBody CommentModifyRequest request,
		@PathVariable Long id) {
		LocalDateTime modifiedAt = LocalDateTime.now();
		return ApiResponse.ok(commentService.modify(request.toServiceRequest(id), modifiedAt));
	}

	@DeleteMapping("/{id}")
	public ApiResponse<CommentDeleteResponse> deleteComment(@PathVariable Long id) {
		return ApiResponse.ok(commentService.delete(id));
	}

}
