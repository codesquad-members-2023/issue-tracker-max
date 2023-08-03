package codesquard.app.label.dto.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LabelReadResponse {
	@JsonProperty("openedMilestoneCount")
	private Long openedMilestoneCount;
	@JsonProperty("labelCount")
	private Long labelCount;
	@JsonProperty("labels")
	private List<LabelsResponse> labels;

	public LabelReadResponse(final Long openedMilestoneCount, final Long labelCount,
		final List<LabelsResponse> labels) {
		this.openedMilestoneCount = openedMilestoneCount;
		this.labelCount = labelCount;
		this.labels = labels;
	}

	public LabelReadResponse success() {
		return new LabelReadResponse(openedMilestoneCount, labelCount, labels);
	}
}
