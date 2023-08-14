package org.presents.issuetracker.comment.controller;

import lombok.RequiredArgsConstructor;
import org.presents.issuetracker.comment.dto.request.CommentCreateRequest;
import org.presents.issuetracker.comment.dto.response.CommentResponse;
import org.presents.issuetracker.comment.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentResponse> create(@RequestBody CommentCreateRequest commentCreateRequest) {
        CommentResponse commentResponse = commentService.create(commentCreateRequest);

        return ResponseEntity.ok().body(commentResponse);
    }
}
