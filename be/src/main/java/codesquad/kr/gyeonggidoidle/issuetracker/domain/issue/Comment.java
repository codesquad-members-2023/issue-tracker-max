package codesquad.kr.gyeonggidoidle.issuetracker.domain.issue;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Comment {

    private final Long authorId;
    private final Long issueId;
    private String contents;
    private String file;

    @Builder
    public Comment(Long authorId, Long issueId, String contents, String file) {
        this.authorId = authorId;
        this.issueId = issueId;
        this.contents = contents;
        this.file = file;
    }

    public boolean isContentsExist() {
        return this.contents != null;
    }

    public boolean isFileExist() {
        return this.file != null;
    }
}
