package codesquard.app.milestone.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MilestoneUpdateResponse {
	@JsonProperty("success")
	private final boolean success;

	private MilestoneUpdateResponse(final boolean success) {
		this.success = success;
	}

	public static MilestoneUpdateResponse success() {
		return new MilestoneUpdateResponse(true);
	}
}
