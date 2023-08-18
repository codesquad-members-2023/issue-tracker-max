package org.presents.issuetracker.comment.controller;

import lombok.RequiredArgsConstructor;
import org.presents.issuetracker.comment.dto.request.CommentCreateRequest;
import org.presents.issuetracker.comment.dto.response.CommentResponse;
import org.presents.issuetracker.comment.dto.response.CommentUpdateRequest;
import org.presents.issuetracker.comment.service.CommentService;
import org.presents.issuetracker.global.dto.response.IdResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentResponse> create(@RequestBody CommentCreateRequest commentCreateRequest) {
        CommentResponse commentResponse = commentService.create(commentCreateRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(commentResponse);
    }

    @PutMapping
    public ResponseEntity<IdResponse> update(@RequestBody CommentUpdateRequest commentUpdateRequest) {
        IdResponse idResponse = commentService.update(commentUpdateRequest);

        return ResponseEntity.status(HttpStatus.OK).body(idResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        commentService.delete(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
