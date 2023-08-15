package codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.repository.result;

import lombok.Builder;
import lombok.Getter;

@Getter
public class IssueUpdateResult {

    private final Long issueId;
    private final String title;
    private final Long milestoneId;

    @Builder
    public IssueUpdateResult(Long issueId, String title, Long milestoneId) {
        this.issueId = issueId;
        this.title = title;
        this.milestoneId = milestoneId;
    }
}
