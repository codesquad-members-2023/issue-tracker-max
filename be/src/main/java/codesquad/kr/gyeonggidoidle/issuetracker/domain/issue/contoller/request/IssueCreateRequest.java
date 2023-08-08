package codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.contoller.request;

import codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.service.condition.IssueCreateCondition;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class IssueCreateRequest {

    private final Long authorId;
    private final String title;
    private final String comment;
    private final List<Long> assignees;
    private final List<Long> labels;
    private final Long milestone;
    private final String file;

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

    public static IssueCreateCondition to(IssueCreateRequest request) {
        return IssueCreateCondition.builder()
                .authorId(request.getAuthorId())
                .title(request.getTitle())
                .comment(request.getComment())
                .assignees(request.getAssignees())
                .labels(request.getLabels())
                .milestone(request.getMilestone())
                .file(request.getFile())
                .build();
    }
}
