package codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.repository.vo;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

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
