package codesquard.app.milestone.dto.response;

import java.util.List;

public class MilestoneReadResponse {
	private Long openedMilestoneCount;
	private Long closedMilestoneCount;
	private Long labelCount;
	private List<MilestonesResponse> milestones;

	public MilestoneReadResponse(Long openedMilestoneCount, Long closedMilestoneCount, Long labelCount,
		List<MilestonesResponse> milestones) {
		this.openedMilestoneCount = openedMilestoneCount;
		this.closedMilestoneCount = closedMilestoneCount;
		this.labelCount = labelCount;
		this.milestones = milestones;
	}

	public MilestoneReadResponse success() {
		return new MilestoneReadResponse(openedMilestoneCount, closedMilestoneCount, labelCount, milestones);
	}

	public Long getOpenedMilestoneCount() {
		return openedMilestoneCount;
	}

	public Long getClosedMilestoneCount() {
		return closedMilestoneCount;
	}

	public Long getLabelCount() {
		return labelCount;
	}

	public List<MilestonesResponse> getMilestones() {
		return milestones;
	}
}
