package codesquad.kr.gyeonggidoidle.issuetracker.domain.milestone;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class Milestone {

    private final Long id;
    private final String name;
    private final LocalDate dueDate;
    private String description;
    private final String status;

    @Builder
    private Milestone(Long id, String name, LocalDate dueDate, String description, String status) {
        this.id = id;
        this.name = name;
        this.dueDate = dueDate;
        this.description = description;
        this.status = status;
    }
}
