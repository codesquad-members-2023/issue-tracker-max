package codesquad.kr.gyeonggidoidle.issuetracker.domain.milestone.controller.response;

import codesquad.kr.gyeonggidoidle.issuetracker.domain.milestone.service.information.MilestoneInformation;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MilestoneResponse {

    private final String name;
    private final Integer openIssueCount;
    private final Integer closedIssueCount;

    @Builder
    private MilestoneResponse(String name, Integer openIssueCount, Integer closedIssueCount) {
        this.name = name;
        this.openIssueCount = openIssueCount;
        this.closedIssueCount = closedIssueCount;
    }

    public static MilestoneResponse from(MilestoneInformation milestoneInformation) {
        return MilestoneResponse.builder()
                .name(milestoneInformation.getName())
                .openIssueCount(milestoneInformation.getOpenIssueCount())
                .closedIssueCount(milestoneInformation.getClosedIssueCount())
                .build();
    }
}
