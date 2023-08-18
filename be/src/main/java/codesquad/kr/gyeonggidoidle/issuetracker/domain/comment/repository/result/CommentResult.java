package codesquad.kr.gyeonggidoidle.issuetracker.domain.comment.repository.result;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResult {

    private final Long id;
    private final Long authorId;
    private final String authorName;
    private final String contents;
    private final LocalDateTime createdAt;

    @Builder
    private CommentResult(Long id, Long authorId, String authorName, String contents, LocalDateTime createdAt) {
        this.id = id;
        this.authorId = authorId;
        this.authorName = authorName;
        this.contents = contents;
        this.createdAt = createdAt;
    }
}
