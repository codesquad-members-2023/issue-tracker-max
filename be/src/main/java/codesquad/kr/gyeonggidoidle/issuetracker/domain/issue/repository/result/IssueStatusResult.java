package codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.repository.result;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class IssueStatusResult {

    private final boolean open;
    private final List<Long> issueIds;

    @Builder
    private IssueStatusResult(boolean open, List<Long> issueIds) {
        this.open = open;
        this.issueIds = issueIds;
    }
}
