package codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.service.condition;

import codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.Comment;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.Issue;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class IssueCreateCondition {

    private final Long authorId;
    private final String title;
    private final String comment;
    private final List<Long> assignees;
    private final List<Long> labels;
    private final Long milestone;
    private final String file;

    @Builder
    public IssueCreateCondition(Long authorId, String title, String comment, List<Long> assignees, List<Long> labels,
                                Long milestone, String file) {
        this.authorId = authorId;
        this.title = title;
        this.comment = comment;
        this.assignees = assignees;
        this.labels = labels;
        this.milestone = milestone;
        this.file = file;
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
                .file(condition.getFile())
                .build();
    }
}
