package codesquad.issueTracker.issue.vo;

import codesquad.issueTracker.milestone.vo.MilestoneVo;
import lombok.Builder;
import lombok.Getter;

@Getter
public class IssueMilestoneVo {
    private IssueMileStoneDetailVo issueMileStoneDetailVo;
    private int completedRatio;

    @Builder
    public IssueMilestoneVo(IssueMileStoneDetailVo issueMileStoneDetailVo, int completedRatio) {
        this.issueMileStoneDetailVo = issueMileStoneDetailVo;
        this.completedRatio = completedRatio;
    }

    public static IssueMilestoneVo from(MilestoneVo milestoneVo) {
        return IssueMilestoneVo.builder()
                .issueMileStoneDetailVo(IssueMileStoneDetailVo.from(milestoneVo))
                .build();
    }

    public IssueMilestoneVo getMilestoneWithRatio(int openCount, int closeCount) {
        return IssueMilestoneVo.builder()
                .issueMileStoneDetailVo(this.issueMileStoneDetailVo)
                .completedRatio(calculateRatio(openCount, closeCount))
                .build();
    }

    private int calculateRatio(int openCount, int closeCount) {
        return closeCount * 100 / (openCount + closeCount) ;
    }
}
