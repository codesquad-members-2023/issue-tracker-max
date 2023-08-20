package codesquad.issueTracker.issue.vo;

import codesquad.issueTracker.milestone.vo.MilestoneVo;
import lombok.Builder;
import lombok.Getter;

@Getter
public class IssueMileStoneDetailVo {
    private Long id;
    private String name;

    @Builder
    public IssueMileStoneDetailVo(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static IssueMileStoneDetailVo from(MilestoneVo milestoneVo) {
        return IssueMileStoneDetailVo.builder()
                .id(milestoneVo.getId())
                .name(milestoneVo.getName())
                .build();
    }
}
