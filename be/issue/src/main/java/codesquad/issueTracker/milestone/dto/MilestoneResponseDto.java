package codesquad.issueTracker.milestone.dto;

import java.util.List;

import codesquad.issueTracker.milestone.vo.MilestoneVo;
import lombok.Getter;

@Getter
public class MilestoneResponseDto {
	int anotherMilestoneCount;
	List<MilestoneVo> milestones;

	public MilestoneResponseDto(int anotherMilestoneCount, List<MilestoneVo> milestones) {
		this.anotherMilestoneCount = anotherMilestoneCount;
		this.milestones = milestones;
	}
}
