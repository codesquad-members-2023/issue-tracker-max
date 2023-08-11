package codesquad.kr.gyeonggidoidle.issuetracker.domain.milestone.service.condition;

import codesquad.kr.gyeonggidoidle.issuetracker.domain.milestone.Milestone;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MilestoneCreateCondition {

    private final String name;
    private final String description;
    private final LocalDate dueDate;

    @Builder
    private MilestoneCreateCondition(String name, String description, LocalDate dueDate) {
        this.name = name;
        this.description = description;
        this.dueDate = dueDate;
    }

    public static Milestone to(MilestoneCreateCondition condition) {
        return Milestone.builder()
                .name(condition.getName())
                .description(condition.getDescription())
                .dueDate(condition.getDueDate())
                .build();
    }
}
