package org.presents.issuetracker.label.entity.vo;

import lombok.Getter;
import org.presents.issuetracker.label.dto.response.LabelDetailResponse;

import java.util.List;

@Getter
public class LabelInfo {
    private int labelCount;
    private int milestoneCount;
    private List<LabelDetailResponse> labels;

    private LabelInfo(int labelCount, int milestoneCount, List<LabelDetailResponse> labels) {
        this.labelCount = labelCount;
        this.milestoneCount = milestoneCount;
        this.labels = labels;
    }

    public static LabelInfo of(int labelCount, int milestoneCount, List<LabelDetailResponse> labelDetails) {
        return new LabelInfo(labelCount, milestoneCount, labelDetails);
    }
}
