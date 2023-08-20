package codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.service.condition;

import codesquad.kr.gyeonggidoidle.issuetracker.domain.comment.Comment;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.Issue;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class IssueCreateCondition {

    private final Long authorId;
    private final String title;
    private String comment;
    private List<Long> assignees;
    private List<Long> labels;
    private Long milestone;

    @Builder
    public IssueCreateCondition(Long authorId, String title, String comment, List<Long> assignees, List<Long> labels,
                                Long milestone) {
        this.authorId = authorId;
        this.title = title;
        this.comment = comment;
        this.assignees = assignees;
        this.labels = labels;
        this.milestone = milestone;
    }

    public static Issue toIssue(IssueCreateCondition condition) {
        return Issue.builder()
                .authorId(condition.getAuthorId())
                .title(condition.getTitle())
                .milestoneId(condition.getMilestone())
                .build();
    }

    public static Comment toComment(Long issueId, IssueCreateCondition condition) {
        return Comment.builder()
                .issueId(issueId)
                .authorId(condition.getAuthorId())
                .contents(condition.comment)
                .build();
    }

    public boolean hasComment() {
        return this.comment != null;
    }

    public boolean hasAssignees() {
        return this.assignees != null && !this.assignees.isEmpty();
    }

    public boolean hasLabels() {
        return this.labels != null && !this.labels.isEmpty();
    }
}
