package codesquad.issueTracker.issue.vo;

import lombok.Builder;
import lombok.Getter;

@Getter
public class IssueMilestoneVo {
    private Long id;
    private String name;
    private int completedRatio;

    @Builder
    public IssueMilestoneVo(Long id, String name, int completedRatio) {
        this.id = id;
        this.name = name;
        this.completedRatio = completedRatio;
    }

    public static IssueMilestoneVo from(IssueMilestoneVo issueMilestone) {
        return IssueMilestoneVo.builder()
                .id(issueMilestone.getId())
                .name(issueMilestone.getName())
                .build();
    }

    public IssueMilestoneVo getMilestoneWithRatio(int openCount, int closeCount) {
        return IssueMilestoneVo.builder()
                .id(this.id)
                .name(this.name)
                .completedRatio(calculateRatio(openCount, closeCount))
                .build();
    }

    private int calculateRatio(int openCount, int closeCount) {
        return closeCount * 100 / (openCount + closeCount) ;
    }
}
