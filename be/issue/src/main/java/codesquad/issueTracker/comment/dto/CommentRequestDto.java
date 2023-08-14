package codesquad.issueTracker.comment.dto;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentRequestDto {

    @NotNull(message = "댓글 내용을 입력해주세요.")
    private String content;

}
