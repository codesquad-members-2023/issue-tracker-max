package codesquard.app.milestone.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import codesquard.app.milestone.entity.MilestoneStatus;

public class MilestoneStatusRequest {
	@JsonProperty("status")
	private String status;

	private MilestoneStatusRequest() {
	}

	private MilestoneStatusRequest(final String status) {
		this.status = status;
	}

	public static MilestoneStatus toStatus(final MilestoneStatusRequest milestoneStatusRequest) {
		if (milestoneStatusRequest.status.equalsIgnoreCase(MilestoneStatus.OPENED_STRING)) {
			return MilestoneStatus.OPENED;
		}

		return MilestoneStatus.CLOSED;
	}
}
