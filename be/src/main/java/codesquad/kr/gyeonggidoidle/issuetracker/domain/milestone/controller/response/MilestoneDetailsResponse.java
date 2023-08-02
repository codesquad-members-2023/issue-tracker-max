package codesquad.kr.gyeonggidoidle.issuetracker.domain.milestone.controller.response;

import codesquad.kr.gyeonggidoidle.issuetracker.domain.milestone.service.information.MilestoneDetailsInformation;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MilestoneDetailsResponse {

    private final Long id;
    private final String name;
    private final String description;
    private final LocalDate dueDate;
    private final Integer openIssueCount;
    private final Integer closedIssuesCount;

    @Builder
    private MilestoneDetailsResponse(Long id, String name, String description, LocalDate dueDate, Integer openIssueCount,
                                    Integer closedIssuesCount) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.dueDate = dueDate;
        this.openIssueCount = openIssueCount;
        this.closedIssuesCount = closedIssuesCount;
    }

    public static List<MilestoneDetailsResponse> from(List<MilestoneDetailsInformation> milestoneDetailsInformations) {
        return milestoneDetailsInformations.stream()
                .map(MilestoneDetailsResponse::from)
                .collect(Collectors.toUnmodifiableList());
    }

    public static MilestoneDetailsResponse from(MilestoneDetailsInformation milestoneDetailsInformation) {
        return MilestoneDetailsResponse.builder()
                .id(milestoneDetailsInformation.getId())
                .name(milestoneDetailsInformation.getName())
                .description(milestoneDetailsInformation.getDescription())
                .dueDate(milestoneDetailsInformation.getDueDate())
                .openIssueCount(milestoneDetailsInformation.getOpenIssueCount())
                .closedIssuesCount(milestoneDetailsInformation.getClosedIssuesCount())
                .build();
    }
}
