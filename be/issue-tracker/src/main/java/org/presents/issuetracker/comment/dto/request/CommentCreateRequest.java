package org.presents.issuetracker.comment.dto.request;

import lombok.Getter;

@Getter
public class CommentCreateRequest {
    private Long issueId;
    private Long authorId;
    private String contents;
}
