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

    private MilestoneResponse(Long id, String name, LocalDateTime deadline, String description, String status) {
        this.id = id;
        this.name = name;
        this.deadline = deadline;
        this.description = description;
        this.status = status;
    }

    public static MilestoneResponse of(Long id, String name, LocalDateTime deadline, String description, String status) {
        return new MilestoneResponse(id, name, deadline, description, status);
    }
}
