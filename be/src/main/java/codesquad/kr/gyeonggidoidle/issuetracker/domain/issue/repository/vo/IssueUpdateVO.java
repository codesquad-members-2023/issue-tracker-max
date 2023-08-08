package codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.repository.vo;

import lombok.Builder;
import lombok.Getter;

@Getter
public class IssueUpdateVO {

    private final Long issueId;
    private final String title;
    private final Long milestoneId;

    @Builder
    public IssueUpdateVO(Long issueId, String title, Long milestoneId) {
        this.issueId = issueId;
        this.title = title;
        this.milestoneId = milestoneId;
    }
}
