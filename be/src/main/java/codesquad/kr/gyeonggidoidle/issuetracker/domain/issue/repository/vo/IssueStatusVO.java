package codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.repository.vo;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class IssueStatusVO {

    private final boolean open;
    private final List<Long> issueIds;

    @Builder
    private IssueStatusVO(boolean open, List<Long> issueIds) {
        this.open = open;
        this.issueIds = issueIds;
    }
}
