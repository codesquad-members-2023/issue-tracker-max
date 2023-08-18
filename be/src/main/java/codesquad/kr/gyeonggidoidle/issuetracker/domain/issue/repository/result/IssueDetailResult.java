package codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.repository.result;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class IssueDetailResult {

    private final Long id;
    private final String author;
    private final String title;
    private final Boolean isOpen;
    private final LocalDateTime createdAt;

    @Builder
    private IssueDetailResult(Long id, String author, String title, Boolean isOpen, LocalDateTime createdAt) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.isOpen = isOpen;
        this.createdAt = createdAt;
    }
}
