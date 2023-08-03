package codesquard.app.milestone.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MilestoneDeleteResponse {
	@JsonProperty("success")
	private final boolean success;

	private MilestoneDeleteResponse(final boolean success) {
		this.success = success;
	}

	public static MilestoneDeleteResponse success() {
		return new MilestoneDeleteResponse(true);
	}
}
