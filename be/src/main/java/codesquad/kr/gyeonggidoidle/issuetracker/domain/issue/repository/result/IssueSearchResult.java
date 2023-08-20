package codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.repository.result;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class IssueSearchResult {

    private final Long id;
    private final String title;
    private final String author;
    private final String milestone;
    private final LocalDateTime createdAt;

    @Builder
    public IssueSearchResult(Long id, String title, String author, String milestone, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.milestone = milestone;
        this.createdAt = createdAt;
    }
}
