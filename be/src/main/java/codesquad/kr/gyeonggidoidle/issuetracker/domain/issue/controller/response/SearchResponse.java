package codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.controller.response;

import codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.service.information.SearchInformation;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class SearchResponse {

    private final Integer openIssueCount;
    private final Integer closedIssueCount;
    private final Integer labelCount;
    private final Integer milestoneCount;
    private final String filter;
    @JsonProperty("issues")
    private final List<IssueResponse> issueResponses;

    @Builder
    public SearchResponse(Integer openIssueCount, Integer closedIssueCount, Integer labelCount, Integer milestoneCount, String filter, List<IssueResponse> issueResponses) {
        this.openIssueCount = openIssueCount;
        this.closedIssueCount = closedIssueCount;
        this.labelCount = labelCount;
        this.milestoneCount = milestoneCount;
        this.filter = filter;
        this.issueResponses = issueResponses;
    }

    public static SearchResponse from(SearchInformation searchInformation) {
        return SearchResponse.builder()
                .openIssueCount(searchInformation.getOpenIssueCount())
                .closedIssueCount(searchInformation.getClosedIssueCount())
                .labelCount(searchInformation.getLabelCount())
                .milestoneCount(searchInformation.getMilestoneCount())
                .filter(searchInformation.getFilter())
                .issueResponses(IssueResponse.from(searchInformation.getIssueInformations()))
                .build();
    }
}
