package codesquad.kr.gyeonggidoidle.issuetracker.domain.milestone.controller.request;

import codesquad.kr.gyeonggidoidle.issuetracker.domain.milestone.service.condition.MilestoneCreateCondition;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.milestone.service.condition.MilestoneUpdateCondition;
import java.time.LocalDate;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class MilestoneRequest {

    @NotBlank
    private String name;
    private String description;
    @NotNull(message = "날짜를 입력해주세요.")
    @FutureOrPresent(message = "유효한 만료 날짜를 입력해주세요.")
    private LocalDate dueDate;

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
