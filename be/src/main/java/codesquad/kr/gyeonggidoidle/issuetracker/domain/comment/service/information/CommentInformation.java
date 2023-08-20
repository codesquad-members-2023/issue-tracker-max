package codesquad.kr.gyeonggidoidle.issuetracker.domain.comment.service.information;

import codesquad.kr.gyeonggidoidle.issuetracker.domain.comment.repository.result.CommentResult;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class CommentInformation {

    private final Long id;
    private final Long authorId;
    private final String authorName;
    private final String contents;
    private final LocalDateTime createdAt;

    @Builder
    private CommentInformation(Long id, Long authorId, String authorName, String contents, LocalDateTime createdAt) {
        this.id = id;
        this.authorId = authorId;
        this.authorName = authorName;
        this.contents = contents;
        this.createdAt = createdAt;
    }

    public static List<CommentInformation> from(List<CommentResult> commentResults) {
        return commentResults.stream()
                .map(CommentInformation::from)
                .collect(Collectors.toUnmodifiableList());
    }

    private static CommentInformation from(CommentResult commentResult) {
        return CommentInformation.builder()
                .id(commentResult.getId())
                .authorId(commentResult.getAuthorId())
                .authorName(commentResult.getAuthorName())
                .contents(commentResult.getContents())
                .createdAt(commentResult.getCreatedAt())
                .build();
    }
}
