package codesquard.app.milestone.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import codesquard.app.milestone.entity.MilestoneStatus;

public class MilestoneStatusRequest {
	@JsonProperty("status")
	private String status;

	private MilestoneStatusRequest(String status) {
		this.status = status;
	}

	public static MilestoneStatus toStatus(MilestoneStatusRequest milestoneStatusRequest) {
		if (milestoneStatusRequest.status.equalsIgnoreCase("OPENED")) {
			return MilestoneStatus.OPENED;
		}

		return MilestoneStatus.CLOSED;
	}
}
