package com.issuetrackermax.controller.comment;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.issuetrackermax.controller.ApiResponse;
import com.issuetrackermax.service.comment.CommentService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/comments")
public class CommentController {
	private final CommentService commentService;

	@PostMapping("/upload")
	public ApiResponse<String> uploadImage(
		@RequestPart MultipartFile multipartFile) {
		return ApiResponse.success(commentService.uploadFile(multipartFile));
	}

}
