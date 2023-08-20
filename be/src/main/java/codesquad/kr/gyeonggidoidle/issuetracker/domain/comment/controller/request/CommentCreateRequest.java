package codesquad.kr.gyeonggidoidle.issuetracker.domain.comment.controller.request;

import codesquad.kr.gyeonggidoidle.issuetracker.domain.comment.service.condition.CommentCreateCondition;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.label.service.condition.LabelCreateCondition;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.label.service.condition.LabelUpdateCondition;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CommentCreateRequest {

    @NotNull
    private Long issueId;
    @NotNull
    private Long authorId;
    @NotNull
    private String contents;

    @Builder
    public CommentCreateRequest(Long issueId, Long authorId, String contents) {
        this.issueId = issueId;
        this.authorId = authorId;
        this.contents = contents;
    }

    public CommentCreateCondition toCommentCreationCondition() {
        return CommentCreateCondition.builder()
                .issueId(this.issueId)
                .authorId(this.authorId)
                .contents(this.contents)
                .build();
    }
}
