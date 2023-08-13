package codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.controller.response;

import codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.service.information.MilestoneFilterInformation;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class MilestoneFilterResponse {

    private final Long id;
    private final String name;
    @JsonInclude(Include.NON_NULL)
    private final Integer openIssueCount;
    @JsonInclude(Include.NON_NULL)
    private final Integer closedIssueCount;

    @Builder
    private MilestoneFilterResponse(Long id, String name, Integer openIssueCount, Integer closedIssueCount) {
        this.id = id;
        this.name = name;
        this.openIssueCount = openIssueCount;
        this.closedIssueCount = closedIssueCount;
    }

    public static List<MilestoneFilterResponse> from(List<MilestoneFilterInformation> informations) {
        return informations.stream()
                .map(MilestoneFilterResponse::from)
                .collect(Collectors.toUnmodifiableList());
    }

    public static MilestoneFilterResponse from(MilestoneFilterInformation information) {
        Integer openIssueCount = information.getOpenIssueCount();

        return MilestoneFilterResponse.builder()
                .id(information.getId())
                .name(information.getName())
                .openIssueCount(information.getOpenIssueCount())
                .closedIssueCount(information.getClosedIssueCount())
                .build();
    }
}
