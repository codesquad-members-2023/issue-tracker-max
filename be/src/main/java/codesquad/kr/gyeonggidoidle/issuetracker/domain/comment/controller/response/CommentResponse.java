package codesquad.kr.gyeonggidoidle.issuetracker.domain.comment.controller.response;

import codesquad.kr.gyeonggidoidle.issuetracker.domain.comment.service.information.CommentInformation;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class CommentResponse {

    private final Long id;
    private final Long authorId;
    private final String authorName;
    private final String contents;
    private final LocalDateTime createdAt;

    @Builder
    private CommentResponse(Long id, Long authorId, String authorName, String contents, LocalDateTime createdAt) {
        this.id = id;
        this.authorId = authorId;
        this.authorName = authorName;
        this.contents = contents;
        this.createdAt = createdAt;
    }

    public static List<CommentResponse> from(List<CommentInformation> commentInformations) {
        return commentInformations.stream()
                .map(CommentResponse::from)
                .collect(Collectors.toUnmodifiableList());
    }

    private static CommentResponse from(CommentInformation commentInformation) {
        return CommentResponse.builder()
                .id(commentInformation.getId())
                .authorId(commentInformation.getAuthorId())
                .authorName(commentInformation.getAuthorName())
                .contents(commentInformation.getContents())
                .createdAt(commentInformation.getCreatedAt())
                .build();
    }

}
