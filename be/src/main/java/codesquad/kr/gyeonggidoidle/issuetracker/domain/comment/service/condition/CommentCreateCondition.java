package codesquad.kr.gyeonggidoidle.issuetracker.domain.comment.service.condition;

import codesquad.kr.gyeonggidoidle.issuetracker.domain.comment.Comment;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CommentCreateCondition {

    private final Long issueId;
    private final Long authorId;
    private final String contents;

    @Builder
    private CommentCreateCondition(Long issueId, Long authorId, String contents) {
        this.issueId = issueId;
        this.authorId = authorId;
        this.contents = contents;
    }

    public Comment toComment() {
        return Comment.builder()
                .issueId(this.issueId)
                .authorId(this.authorId)
                .contents(this.contents)
                .build();
    }
}
