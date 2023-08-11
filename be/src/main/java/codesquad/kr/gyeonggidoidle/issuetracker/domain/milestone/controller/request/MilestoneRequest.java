package codesquad.kr.gyeonggidoidle.issuetracker.domain.milestone.controller.request;

import codesquad.kr.gyeonggidoidle.issuetracker.domain.milestone.service.condition.MilestoneCreateCondition;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.milestone.service.condition.MilestoneUpdateCondition;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MilestoneRequest {

    private final String name;
    private final String description;
    private final LocalDate dueDate;

    @Builder
    public MilestoneRequest(String name, String description, LocalDate dueDate) {
        this.name = name;
        this.description = description;
        this.dueDate = dueDate;
    }

    public static MilestoneCreateCondition to(MilestoneRequest request) {
        return MilestoneCreateCondition.builder()
                .name(request.getName())
                .description(request.getDescription())
                .dueDate(request.getDueDate())
                .build();
    }

    public static MilestoneUpdateCondition to(Long milestoneId, MilestoneRequest request) {
        return MilestoneUpdateCondition.builder()
                .id(milestoneId)
                .name(request.getName())
                .description(request.getDescription())
                .dueDate(request.getDueDate())
                .build();
    }
}
