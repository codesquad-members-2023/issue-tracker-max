package codesquad.kr.gyeonggidoidle.issuetracker.domain.comment;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Comment {

    private final Long id;
    private final Long authorId;
    private final Long issueId;
    private final String contents;

    @Builder
    private Comment(Long id, Long authorId, Long issueId, String contents) {
        this.id = id;
        this.authorId = authorId;
        this.issueId = issueId;
        this.contents = contents;
    }

    public boolean isContentsExist() {
        return this.contents != null;
    }
}
