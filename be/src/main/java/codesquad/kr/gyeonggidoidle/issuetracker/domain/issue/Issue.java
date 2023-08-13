package codesquad.kr.gyeonggidoidle.issuetracker.domain.issue;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Issue {

    private final Long id;
    private final Long authorId;
    private Long milestoneId;
    private final String title;
    private final String status;
    private final LocalDateTime createdAt;

    @Builder
    private Issue(Long id, Long authorId, Long milestoneId, String title, String status, LocalDateTime createdAt) {
        this.id = id;
        this.authorId = authorId;
        this.milestoneId = milestoneId;
        this.title = title;
        this.status = status;
        this.createdAt = createdAt;
    }
}
