package codesquad.issueTracker.issue.dto;

import codesquad.issueTracker.issue.domain.Issue;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
public class IssueResponseDto {

    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private boolean isClose;

    @Builder
    public IssueResponseDto(Long id, String title, String content, LocalDateTime createdAt, boolean isClose) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.isClose = isClose;
    }

    public static IssueResponseDto from(Issue issue) {
        return IssueResponseDto.builder()
                .id(issue.getId())
                .title(issue.getTitle())
                .content(issue.getContent())
                .createdAt(issue.getCreatedAt())
                .isClose(issue.getIsClosed())
                .build();
    }
}
