package codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.controller.request;

import codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.service.condition.IssueStatusPatchCondition;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
public class IssueStatusPatchRequest {

    @JsonProperty("isOpen")
    private boolean open;
    private List<Long> issues;

    @Builder
    private IssueStatusPatchRequest(boolean open, List<Long> issues) {
        this.open = open;
        this.issues = issues;
    }

    public static IssueStatusPatchCondition to(IssueStatusPatchRequest request) {
        return IssueStatusPatchCondition.builder()
                .open(request.isOpen())
                .issueIds(request.getIssues())
                .build();
    }

    public static IssueStatusPatchCondition to(Long issueId, IssueStatusPatchRequest request) {
        return IssueStatusPatchCondition.builder()
                .open(request.isOpen())
                .issueIds(List.of(issueId))
                .build();
    }
}
