package org.presents.issuetracker.issue.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IssueCreateRequestDto {
    private String title;
    private String contents;
    private Long authorId;
    private List<Long> assigneeIds;
    private List<Long> labelIds;
    private Long milestoneId;
}
