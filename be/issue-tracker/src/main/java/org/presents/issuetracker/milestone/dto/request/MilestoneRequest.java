package org.presents.issuetracker.milestone.dto.request;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter
public class MilestoneRequest {
    private Long id;

    @NotBlank
    private String name;

    private LocalDateTime deadline;

    private String description;

    private String status;
}
