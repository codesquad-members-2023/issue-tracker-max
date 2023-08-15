package codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.service.condition;

import codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.repository.result.IssueStatusResult;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class IssueStatusPatchCondition {

    private final boolean open;
    private final List<Long> issueIds;

    @Builder
    private IssueStatusPatchCondition(boolean open, List<Long> issueIds) {
        this.open = open;
        this.issueIds = issueIds;
    }

    public static IssueStatusResult to(IssueStatusPatchCondition condition) {
        return IssueStatusResult.builder()
                .open(condition.isOpen())
                .issueIds(condition.getIssueIds())
                .build();
    }
}
