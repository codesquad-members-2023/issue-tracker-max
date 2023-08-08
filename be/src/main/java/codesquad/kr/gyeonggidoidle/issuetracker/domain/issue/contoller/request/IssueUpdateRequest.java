package codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.contoller.request;

import codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.service.condition.IssueUpdateCondition;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class IssueUpdateRequest {

    private final String title;
    private final List<Long> assignees;
    private final List<Long> labels;
    private final Long milestone;

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
