package codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.controller.request;

import codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.service.condition.IssueUpdateCondition;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class IssueUpdateRequest {

    private String title;
    private List<Long> assignees;
    private List<Long> labels;
    private Long milestone;

    @Builder
    private IssueUpdateRequest(String title, List<Long> assignees, List<Long> labels, Long milestone) {
        this.title = title;
        this.assignees = assignees;
        this.labels = labels;
        this.milestone = milestone;
    }

    public static IssueUpdateCondition to(Long issueId, IssueUpdateRequest request) {
        return IssueUpdateCondition.builder()
                .issueId(issueId)
                .title(request.getTitle())
                .assignees(request.getAssignees())
                .labels(request.getLabels())
                .milestone(request.getMilestone())
                .build();
    }
}
