package codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.contoller.response;

import codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.service.information.FilterListInformation;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class FilterListResponse {

    private final List<AssigneeFilterResponse> assignees;
    @JsonInclude(Include.NON_NULL)
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
