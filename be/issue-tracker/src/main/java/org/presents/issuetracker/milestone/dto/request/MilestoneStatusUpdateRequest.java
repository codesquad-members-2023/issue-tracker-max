package org.presents.issuetracker.milestone.dto.request;

import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
public class MilestoneStatusUpdateRequest {
    @NotNull
    private Long id;
    @NotEmpty
    private String status;
}
