package codesquad.kr.gyeonggidoidle.issuetracker.domain.milestone.service.condition;

import codesquad.kr.gyeonggidoidle.issuetracker.domain.milestone.Milestone;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MilestoneUpdateCondition {

    private final Long id;
    private final String name;
    private final String description;
    private final LocalDate dueDate;

    @Builder
    private MilestoneUpdateCondition(Long id, String name, String description, LocalDate dueDate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.dueDate = dueDate;
    }

    public static Milestone to(MilestoneUpdateCondition condition) {
        return Milestone.builder()
                .id(condition.getId())
                .name(condition.getName())
                .description(condition.getDescription())
                .dueDate(condition.getDueDate())
                .build();
    }
}
