package codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.service.condition;

import codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.repository.result.IssueUpdateResult;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class IssueUpdateCondition {

    private final Long issueId;
    private final String title;
    private List<Long> assignees;
    private List<Long> labels;
    private Long milestone;

    @Builder
    public IssueUpdateCondition(Long issueId, String title, List<Long> assignees, List<Long> labels, Long milestone) {
        this.issueId = issueId;
        this.title = title;
        this.assignees = assignees;
        this.labels = labels;
        this.milestone = milestone;
    }

    public static IssueUpdateResult to(IssueUpdateCondition condition) {
        return IssueUpdateResult.builder()
                .issueId(condition.getIssueId())
                .title(condition.getTitle())
                .milestoneId(condition.getMilestone())
                .build();
    }
}
