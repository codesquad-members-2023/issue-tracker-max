package codesquad.issueTracker.comment.controller;

import static codesquad.issueTracker.global.exception.SuccessCode.*;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import codesquad.issueTracker.comment.dto.CommentRequestDto;
import codesquad.issueTracker.comment.dto.CommentResponseDto;
import codesquad.issueTracker.comment.service.CommentService;
import codesquad.issueTracker.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentController {

	private final CommentService commentService;

	@GetMapping("/issues/{issueId}/comments")
	public ApiResponse<List<CommentResponseDto>> getComments(@PathVariable Long issueId) {
		List<CommentResponseDto> commentResponseDtos = commentService.getComments(issueId);

		return ApiResponse.success(SUCCESS.getStatus(), commentResponseDtos);
	}

	@PostMapping("/issues/{issueId}/comments")
	public ApiResponse<String> save(@PathVariable Long issueId, @RequestBody CommentRequestDto commentRequestDto,
								HttpServletRequest request) {
		Long userId = findUserIdByHttpRequest(request);
		commentService.save(userId, issueId, commentRequestDto);

		return ApiResponse.success(SUCCESS.getStatus(), SUCCESS.getMessage());
	}

	private Long findUserIdByHttpRequest(HttpServletRequest request) {
		return Long.parseLong(String.valueOf(request.getAttribute("userId")));
	}

	@PatchMapping("/issues/comments/{commentId}")
	public ApiResponse<String> modify(@PathVariable Long commentId,
		@RequestBody CommentRequestDto commentRequestDto) {
		commentService.modify(commentId, commentRequestDto);
		return ApiResponse.success(SUCCESS.getStatus(), SUCCESS.getMessage());
	}

	@DeleteMapping("issues/comments/{commentId}")
	public ApiResponse<String> delete(@PathVariable Long commentId) {
		commentService.delete(commentId);
		return ApiResponse.success(SUCCESS.getStatus(), SUCCESS.getMessage());
	}
}
