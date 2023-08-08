package codesquad.kr.gyeonggidoidle.issuetracker.domain.milestone.repository.vo;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class MilestoneDetailsVO {

    private final Long id;
    private final String name;
    private final String description;
    private final LocalDate dueDate;
    private final Integer openIssueCount;
    private final Integer closedIssuesCount;

    @Builder
    private MilestoneDetailsVO(Long id, String name, String description, LocalDate dueDate, Integer openIssueCount,
                               Integer closedIssuesCount) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.dueDate = dueDate;
        this.openIssueCount = openIssueCount;
        this.closedIssuesCount = closedIssuesCount;
    }
}
