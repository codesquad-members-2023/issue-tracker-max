package codesquad.kr.gyeonggidoidle.issuetracker.domain.stat.repository.vo;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MilestoneStatVO {

    private final Integer openMilestoneCount;
    private final Integer closeMilestoneCount;
    private final Integer labelCount;

    @Builder
    private MilestoneStatVO(Integer openMilestoneCount, Integer closeMilestoneCount, Integer labelCount) {
        this.openMilestoneCount = openMilestoneCount;
        this.closeMilestoneCount = closeMilestoneCount;
        this.labelCount = labelCount;
    }
}
