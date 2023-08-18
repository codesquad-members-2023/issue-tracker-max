package codesquad.kr.gyeonggidoidle.issuetracker.domain.comment.controller;

import codesquad.kr.gyeonggidoidle.issuetracker.domain.comment.controller.request.CommentCreateRequest;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.comment.controller.request.CommentUpdateRequest;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.comment.service.CommentService;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.controller.response.ApiResponse;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.label.controller.request.LabelRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ApiResponse create(@RequestBody @Valid CommentCreateRequest request) {
        commentService.saveComment(request.toCommentCreationCondition());

        return ApiResponse.success(HttpStatus.OK);
    }

    @PatchMapping("/{commentId}")
    public ApiResponse update(@PathVariable Long commentId, @RequestBody @Valid CommentUpdateRequest commentUpdateRequest) {
        commentService.updateComment(commentUpdateRequest.toCommentUpdateCondition(commentId));
        return ApiResponse.success(HttpStatus.OK);
    }
    @DeleteMapping("/{commentId}")
    public ApiResponse delete(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return ApiResponse.success(HttpStatus.OK);
    }
}
