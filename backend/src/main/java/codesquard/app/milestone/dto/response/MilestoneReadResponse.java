package codesquard.app.milestone.dto.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MilestoneReadResponse {
	@JsonProperty("openedMilestoneCount")
	private Long openedMilestoneCount;
	@JsonProperty("closedMilestoneCount")
	private Long closedMilestoneCount;
	@JsonProperty("labelCount")
	private Long labelCount;
	@JsonProperty("status")
	private String status;
	@JsonProperty("milestones")
	private List<MilestonesResponse> milestones;

	public MilestoneReadResponse(final Long openedMilestoneCount, final Long closedMilestoneCount,
		final Long labelCount, final String status, final List<MilestonesResponse> milestones) {
		this.openedMilestoneCount = openedMilestoneCount;
		this.closedMilestoneCount = closedMilestoneCount;
		this.labelCount = labelCount;
		this.status = status;
		this.milestones = milestones;
	}

	public MilestoneReadResponse success() {
		return new MilestoneReadResponse(openedMilestoneCount, closedMilestoneCount, labelCount, status, milestones);
	}
}
