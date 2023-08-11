package codesquad.issueTracker.label.dto;

import java.util.List;

import codesquad.issueTracker.label.vo.LabelVo;
import lombok.Getter;

@Getter
public class LabelResponseDto {
	private List<LabelVo> labels;
	private int milestoneCount;

	public LabelResponseDto(List<LabelVo> labels, int milestoneCount) {
		this.labels = labels;
		this.milestoneCount = milestoneCount;
	}

	public static LabelResponseDto of(List<LabelVo> labels, int milestoneCount) {
		return new LabelResponseDto(labels, milestoneCount);
	}
}
