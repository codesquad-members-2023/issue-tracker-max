package codesquad.issueTracker.issue.dto.filter;

import codesquad.issueTracker.milestone.domain.Milestone;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MilestoneDetailResponseDto {

    private Long id;
    private String name;

    public static MilestoneDetailResponseDto of(Milestone milestone) {
        if (milestone == null) {
            return new MilestoneDetailResponseDto();
        }
        return MilestoneDetailResponseDto.builder()
                .id(milestone.getId())
                .name(milestone.getName())
                .build();
    }

    @Builder
    public MilestoneDetailResponseDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}