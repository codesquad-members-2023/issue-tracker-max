package codesquad.issueTracker.comment.controller;

import static codesquad.issueTracker.global.exception.SuccessCode.*;

import codesquad.issueTracker.comment.dto.CommentResponseDto;
import codesquad.issueTracker.comment.service.CommentService;
import codesquad.issueTracker.global.ApiResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
