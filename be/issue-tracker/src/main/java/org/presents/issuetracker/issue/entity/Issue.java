package org.presents.issuetracker.issue.entity;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class Issue {
    private Long issueId;
    private Long authorId;
    private Long milestoneId;
    private String title;
    private String contents;
    private LocalDateTime createdAt;
    private String status;
}
