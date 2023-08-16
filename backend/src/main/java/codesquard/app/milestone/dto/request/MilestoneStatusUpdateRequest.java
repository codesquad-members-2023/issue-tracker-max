package codesquard.app.milestone.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import codesquard.app.milestone.entity.MilestoneStatus;

public class MilestoneStatusUpdateRequest {
	@JsonProperty("status")
	private String status;

	private MilestoneStatusUpdateRequest() {
	}

	public MilestoneStatusUpdateRequest(final String status) {
		this.status = status;
	}

	public static MilestoneStatus toStatus(final MilestoneStatusUpdateRequest milestoneStatusUpdateRequest) {
		if (milestoneStatusUpdateRequest.status.equalsIgnoreCase(MilestoneStatus.OPENED_STRING)) {
			return MilestoneStatus.OPENED;
		}

		return MilestoneStatus.CLOSED;
	}
}
