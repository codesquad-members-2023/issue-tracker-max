package codesquad.issueTracker.issue.dto;

import codesquad.issueTracker.milestone.vo.MilestoneVo;
import lombok.Builder;
import lombok.Getter;

@Getter
public class IssueMilestoneResponseDto {
    private Long id;
    private String name;

    @Builder
    public IssueMilestoneResponseDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static IssueMilestoneResponseDto from(MilestoneVo milestoneVo) {
        return builder()
                .id(milestoneVo.getId())
                .name(milestoneVo.getName())
                .build();
    }
}
