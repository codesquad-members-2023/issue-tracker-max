package org.presents.issuetracker.comment.dto.response;

import lombok.Getter;

@Getter
public class CommentUpdateRequest {
    private Long id;
    private String contents;
}
