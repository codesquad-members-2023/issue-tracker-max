package codesquad.issueTracker.comment.dto;

import codesquad.issueTracker.comment.vo.CommentUser;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CommentResponseDto {

    private Long id;
    private LocalDateTime createdAt;
    private String content;
    private CommentUser writer;

    @Builder
    public CommentResponseDto(Long id, LocalDateTime createdAt, String content, CommentUser writer) {
        this.id = id;
        this.createdAt = createdAt;
        this.content = content;
        this.writer = writer;
    }
}
