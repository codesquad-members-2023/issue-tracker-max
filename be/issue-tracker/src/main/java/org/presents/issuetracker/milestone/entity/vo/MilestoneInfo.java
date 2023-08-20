package org.presents.issuetracker.milestone.entity.vo;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import org.presents.issuetracker.milestone.dto.response.MilestoneResponse;

@Getter
public class MilestoneInfo {
    private int labelCount;
    private int milestoneCount;
    private int openMilestoneCount;
    private int closedMilestoneCount;
    private List<MilestoneResponse> milestones;

    @Builder
    public MilestoneInfo(int labelCount, int milestoneCount, int openMilestoneCount, int closedMilestoneCount, List<MilestoneResponse> milestones) {
        this.labelCount = labelCount;
        this.milestoneCount = milestoneCount;
        this.openMilestoneCount = openMilestoneCount;
        this.closedMilestoneCount = closedMilestoneCount;
        this.milestones = milestones;
    }
}
