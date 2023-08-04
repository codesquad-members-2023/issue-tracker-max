package codesquard.app.milestone.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MilestoneStatusUpdateResponse {
	@JsonProperty("success")
	private final boolean success;

	private MilestoneStatusUpdateResponse(final boolean success) {
		this.success = success;
	}

	public static MilestoneStatusUpdateResponse success() {
		return new MilestoneStatusUpdateResponse(true);
	}
}
