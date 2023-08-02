package codesquard.app.milestone.dto.request;

import codesquard.app.milestone.entity.MilestoneStatus;

public class MilestoneStatusRequest {
	private String status;

	private MilestoneStatusRequest() {
	}

	public MilestoneStatusRequest(String status) {
		this.status = status;
	}

	public static MilestoneStatus toStatus(MilestoneStatusRequest milestoneStatusRequest) {
		if (milestoneStatusRequest.status.equalsIgnoreCase("OPENED")) {
			return MilestoneStatus.OPENED;
		}

		return MilestoneStatus.CLOSED;
	}

	public String getStatus() {
		return status;
	}
}
