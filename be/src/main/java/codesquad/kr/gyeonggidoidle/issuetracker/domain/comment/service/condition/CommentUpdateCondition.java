package codesquad.kr.gyeonggidoidle.issuetracker.domain.comment.service.condition;

import codesquad.kr.gyeonggidoidle.issuetracker.domain.comment.Comment;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CommentUpdateCondition {

    private final Long commentId;
    private final String contents;

    @Builder
    private CommentUpdateCondition(Long commentId, String contents) {
        this.commentId = commentId;
        this.contents = contents;
    }

    public Comment toComment() {
        return Comment.builder()
                .id(this.commentId)
                .contents(this.contents)
                .build();

    }
}
