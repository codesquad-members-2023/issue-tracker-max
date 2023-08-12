package codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.controller.request;

import codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.service.condition.IssueStatusCondition;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class IssueStatusRequest {

    @JsonProperty("isOpen")
    private boolean open;
    private List<Long> issues;

    @Builder
    private IssueStatusRequest(boolean open, List<Long> issues) {
        this.open = open;
        this.issues = issues;
    }

    public static IssueStatusCondition to(IssueStatusRequest request) {
        return IssueStatusCondition.builder()
                .open(request.isOpen())
                .issueIds(request.getIssues())
                .build();
    }

    public static IssueStatusCondition to(Long issueId, IssueStatusRequest request) {
        return IssueStatusCondition.builder()
                .open(request.isOpen())
                .issueIds(List.of(issueId))
                .build();
    }
}
