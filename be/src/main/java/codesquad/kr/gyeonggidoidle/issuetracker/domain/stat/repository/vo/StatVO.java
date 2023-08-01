package codesquad.kr.gyeonggidoidle.issuetracker.domain.stat.repository.vo;

import lombok.Builder;
import lombok.Getter;

@Getter
public class StatVO {

    private final Integer openIssueCount;
    private final Integer closedIssueCount;
    private final Integer milestoneCount;
    private final Integer labelCount;

    @Builder
    private StatVO(Integer openIssueCount, Integer closedIssueCount, Integer milestoneCount, Integer labelCount) {
        this.openIssueCount = openIssueCount;
        this.closedIssueCount = closedIssueCount;
        this.milestoneCount = milestoneCount;
        this.labelCount = labelCount;
    }
}
