package codesquard.app.milestone.dto.response;

public class MilestoneStatusUpdateResponse {
	private final boolean success;

	private MilestoneStatusUpdateResponse(boolean success) {
		this.success = success;
	}

	public static MilestoneStatusUpdateResponse success() {
		return new MilestoneStatusUpdateResponse(true);
	}

	public boolean isSuccess() {
		return success;
	}
}
