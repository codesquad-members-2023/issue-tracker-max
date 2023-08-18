package codesquad.kr.gyeonggidoidle.issuetracker.domain.milestone.repository.vo;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MilestoneVO {

    private final String name;
    private final Integer openIssueCount;
    private final Integer closedIssueCount;

    @Builder
    private MilestoneVO(String name, Integer openIssueCount, Integer closedIssueCount) {
        this.name = name;
        this.openIssueCount = openIssueCount;
        this.closedIssueCount = closedIssueCount;
    }
}
