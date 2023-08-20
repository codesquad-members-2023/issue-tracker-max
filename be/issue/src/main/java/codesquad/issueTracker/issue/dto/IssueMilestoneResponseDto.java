package codesquad.issueTracker.issue.dto;

import codesquad.issueTracker.issue.vo.IssueMileStoneDetailVo;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class IssueMilestoneResponseDto {
    private List<IssueMileStoneDetailVo> milestones;

    @Builder
    public IssueMilestoneResponseDto(List<IssueMileStoneDetailVo> milestones) {
        this.milestones = milestones;
    }

    public static IssueMilestoneResponseDto from(List<IssueMileStoneDetailVo> milestones) {
        return builder()
                .milestones(milestones)
                .build();
    }
}
