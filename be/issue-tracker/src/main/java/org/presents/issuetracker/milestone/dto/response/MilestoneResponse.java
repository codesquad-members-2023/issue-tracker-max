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

    private MilestoneResponse(Long id, String name, LocalDateTime deadline, String description, String status, int progress) {
        this.id = id;
        this.name = name;
        this.deadline = deadline;
        this.description = description;
        this.status = status;
        this.progress = progress;
    }

    public static MilestoneResponse of(Long id, String name, LocalDateTime deadline, String description, String status, int progress) {
        return new MilestoneResponse(id, name, deadline, description, status, progress);
    }
}
