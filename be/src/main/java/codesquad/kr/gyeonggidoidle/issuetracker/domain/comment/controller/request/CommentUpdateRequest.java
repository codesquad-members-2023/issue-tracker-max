package codesquad.kr.gyeonggidoidle.issuetracker.domain.comment.controller.request;

import codesquad.kr.gyeonggidoidle.issuetracker.domain.comment.service.condition.CommentUpdateCondition;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class CommentUpdateRequest {

    @NotNull
    private String contents;

    @Builder
    private CommentUpdateRequest(String contents) {
        this.contents = contents;
    }

    public CommentUpdateCondition toCommentUpdateCondition(Long commentId) {
        return CommentUpdateCondition.builder()
                .commentId(commentId)
                .contents(this.contents)
                .build();
    }
}
