package codesquad.kr.gyeonggidoidle.issuetracker.domain.milestone.service.information;

import codesquad.kr.gyeonggidoidle.issuetracker.domain.milestone.repository.vo.MilestoneVO;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MilestoneInformation {

    private final String name;
    private final Integer openIssueCount;
    private final Integer closedIssueCount;

    @Builder
    private MilestoneInformation(String name, Integer openIssueCount, Integer closedIssueCount) {
        this.name = name;
        this.openIssueCount = openIssueCount;
        this.closedIssueCount = closedIssueCount;
    }

    public static MilestoneInformation from(MilestoneVO milestoneVO) {
        return MilestoneInformation.builder()
                .name(milestoneVO.getName())
                .openIssueCount(milestoneVO.getOpenIssueCount())
                .closedIssueCount(milestoneVO.getClosedIssueCount())
                .build();
    }
}
