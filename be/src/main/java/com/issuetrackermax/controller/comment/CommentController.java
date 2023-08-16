package com.issuetrackermax.controller.comment;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.issuetrackermax.controller.ApiResponse;
import com.issuetrackermax.controller.comment.dto.request.CommentCreateRequest;
import com.issuetrackermax.controller.comment.dto.request.CommentModifyRequest;
import com.issuetrackermax.controller.comment.dto.response.CommentResponse;
import com.issuetrackermax.service.comment.CommentService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/issues")
public class CommentController {
	private static final String MEMBER_ID = "memberId";
	private final CommentService commentService;

	@PatchMapping("/{issueId}/comments/{id}")
	public ApiResponse<Void> modifyComment(@PathVariable Long id,
		@RequestBody CommentModifyRequest commentModifyRequest,
		HttpServletRequest request) {
		Integer memberId = (Integer)request.getAttribute(MEMBER_ID);
		commentService.modifyComment(commentModifyRequest, id, memberId.longValue());
		return ApiResponse.success();
	}

	@PostMapping("/{issueId}/comments")
	public ApiResponse<CommentResponse> post(@RequestBody CommentCreateRequest commentCreateRequest,
		HttpServletRequest request, @PathVariable Long issueId) {
		Integer memberId = (Integer)request.getAttribute(MEMBER_ID);
		CommentResponse commentResponse = commentService.save(commentCreateRequest, issueId, memberId.longValue());
		return ApiResponse.success(commentResponse);
	}

	@DeleteMapping("(/{issueId}/comments/{id}")
	public ApiResponse<Void> deleteComment(@PathVariable Long id, HttpServletRequest request) {
		Integer memberId = (Integer)request.getAttribute(MEMBER_ID);
		commentService.delete(id, memberId.longValue());
		return ApiResponse.success();
	}

	@PostMapping("/upload")
	public ApiResponse<String> uploadImage(
		@RequestPart MultipartFile multipartFile) {
		return ApiResponse.success(commentService.uploadFile(multipartFile));
	}

}
