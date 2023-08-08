package codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.service.condition;

import codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.repository.vo.IssueStatusVO;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class IssueStatusCondition {

    private final boolean open;
    private final List<Long> issueIds;

    @Builder
    private IssueStatusCondition(boolean open, List<Long> issueIds) {
        this.open = open;
        this.issueIds = issueIds;
    }

    public static IssueStatusVO to(IssueStatusCondition condition) {
        return IssueStatusVO.builder()
                .open(condition.isOpen())
                .issueIds(condition.getIssueIds())
                .build();
    }
}
