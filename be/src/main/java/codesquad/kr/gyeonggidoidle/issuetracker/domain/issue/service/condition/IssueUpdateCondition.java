package codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.service.condition;

import codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.repository.vo.IssueUpdateVO;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class IssueUpdateCondition {

    private final Long issueId;
    private final String title;
    private final List<Long> assignees;
    private final List<Long> labels;
    private final Long milestone;

    @Builder
    public IssueUpdateCondition(Long issueId, String title, List<Long> assignees, List<Long> labels, Long milestone) {
        this.issueId = issueId;
        this.title = title;
        this.assignees = assignees;
        this.labels = labels;
        this.milestone = milestone;
    }

    public static IssueUpdateVO to(IssueUpdateCondition condition) {
        return IssueUpdateVO.builder()
                .issueId(condition.getIssueId())
                .title(condition.getTitle())
                .milestoneId(condition.getMilestone())
                .build();
    }
}
