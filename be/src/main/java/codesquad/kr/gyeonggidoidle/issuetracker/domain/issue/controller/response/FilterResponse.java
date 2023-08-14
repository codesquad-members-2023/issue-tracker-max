package codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.controller.response;

import codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.service.information.FilterInformation;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class FilterResponse {

    private final Integer openIssueCount;
    private final Integer closedIssueCount;
    private final Integer labelCount;
    private final Integer milestoneCount;
    private final String filter;
    @JsonProperty("issues")
    private final List<IssueResponse> issueResponses;

    @Builder
    public FilterResponse(Integer openIssueCount, Integer closedIssueCount, Integer labelCount, Integer milestoneCount, String filter, List<IssueResponse> issueResponses) {
        this.openIssueCount = openIssueCount;
        this.closedIssueCount = closedIssueCount;
        this.labelCount = labelCount;
        this.milestoneCount = milestoneCount;
        this.filter = filter;
        this.issueResponses = issueResponses;
    }

    public static FilterResponse from(FilterInformation filterInformation) {
        return FilterResponse.builder()
                .openIssueCount(filterInformation.getOpenIssueCount())
                .closedIssueCount(filterInformation.getClosedIssueCount())
                .labelCount(filterInformation.getLabelCount())
                .milestoneCount(filterInformation.getMilestoneCount())
                .filter(filterInformation.getFilter())
                .issueResponses(IssueResponse.from(filterInformation.getIssueInformations()))
                .build();
    }
}
