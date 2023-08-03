package codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.contoller.response;

import codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.service.information.FilterListInformation;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class FilterListResponse {

    private final List<AssigneeFilterResponse> assignees;
    private final List<AuthorFilterResponse> authors;
    private final List<LabelFilterResponse> labels;
    private final List<MilestoneFilterResponse> milestones;

    @Builder
    private FilterListResponse(List<AssigneeFilterResponse> assignees, List<AuthorFilterResponse> authors,
                               List<LabelFilterResponse> labels, List<MilestoneFilterResponse> milestones) {
        this.assignees = assignees;
        this.authors = authors;
        this.labels = labels;
        this.milestones = milestones;
    }

    public static FilterListResponse from(FilterListInformation information) {
        return FilterListResponse.builder()
                .assignees(AssigneeFilterResponse.from(information.getAssigneeFilterInformations()))
                .authors(AuthorFilterResponse.from(information.getAuthorFilterInformations()))
                .labels(LabelFilterResponse.from(information.getLabelFilterInformations()))
                .milestones(MilestoneFilterResponse.from(information.getMilestoneFilterInformations()))
                .build();
    }
}
