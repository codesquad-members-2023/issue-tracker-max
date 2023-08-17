package codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.controller.request;

import codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.service.condition.IssueCreateCondition;
import java.util.List;
import javax.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class IssueCreateRequest {

    private Long authorId;
    @NotEmpty
    private String title;
    private String comment;
    private List<Long> assignees;
    private List<Long> labels;
    private Long milestone;
    private String file;

    @Builder
    private IssueCreateRequest(Long authorId, String title, String comment, List<Long> assignees, List<Long> labels,
                               Long milestone, String file) {
        this.authorId = authorId;
        this.title = title;
        this.comment = comment;
        this.assignees = assignees;
        this.labels = labels;
        this.milestone = milestone;
        this.file = file;
    }

    public static IssueCreateCondition to(IssueCreateRequest request, Long memberId) {
        return IssueCreateCondition.builder()
                .authorId(memberId)
                .title(request.getTitle())
                .comment(request.getComment())
                .assignees(request.getAssignees())
                .labels(request.getLabels())
                .milestone(request.getMilestone())
                .file(request.getFile())
                .build();
    }
}
