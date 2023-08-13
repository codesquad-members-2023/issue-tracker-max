package codesquad.issueTracker.milestone.dto;

import java.util.List;

import codesquad.issueTracker.milestone.vo.MilestoneVo;
import lombok.Getter;

@Getter
public class MilestoneResponseDto {
	private int labelCount;
	private int anotherMilestoneCount;
	private List<MilestoneVo> milestones;

	public MilestoneResponseDto(int labelCount, int anotherMilestoneCount, List<MilestoneVo> milestones) {
		this.labelCount = labelCount;
		this.anotherMilestoneCount = anotherMilestoneCount;
		this.milestones = milestones;
	}
}
