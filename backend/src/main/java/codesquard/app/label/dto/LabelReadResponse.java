package codesquard.app.label.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LabelReadResponse {
	@JsonProperty("openedMilestoneCount")
	private Long openedMilestoneCount;
	@JsonProperty("closedMilestoneCount")
	private Long closedMilestoneCount;
	@JsonProperty("labelCount")
	private Long labelCount;
	@JsonProperty("labels")
	private List<LabelsResponse> labels;

	public LabelReadResponse(Long openedMilestoneCount, Long closedMilestoneCount, Long labelCount,
		List<LabelsResponse> labels) {
		this.openedMilestoneCount = openedMilestoneCount;
		this.closedMilestoneCount = closedMilestoneCount;
		this.labelCount = labelCount;
		this.labels = labels;
	}

	public LabelReadResponse success() {
		return new LabelReadResponse(openedMilestoneCount, closedMilestoneCount, labelCount, labels);
	}
}
