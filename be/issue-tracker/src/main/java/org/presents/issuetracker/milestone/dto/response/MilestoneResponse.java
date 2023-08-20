package org.presents.issuetracker.milestone.dto.response;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MilestoneResponse {
    private Long id;
    private String name;
    private LocalDateTime deadline;
    private String description;
    private String status;
    private int progress;
    private int openIssueCount;
    private int closedIssueCount;

    private MilestoneResponse(Long id, String name, LocalDateTime deadline, String description, String status,
        int progress, int openIssueCount, int closedIssueCount) {
        this.id = id;
        this.name = name;
        this.deadline = deadline;
        this.description = description;
        this.status = status;
        this.progress = progress;
        this.openIssueCount = openIssueCount;
        this.closedIssueCount = closedIssueCount;
    }

    public static MilestoneResponse of(Long id, String name, LocalDateTime deadline, String description, String status,
        int progress, int openIssueCount, int closedIssueCount) {
        return new MilestoneResponse(id, name, deadline, description, status, progress, openIssueCount, closedIssueCount);
    }
}
