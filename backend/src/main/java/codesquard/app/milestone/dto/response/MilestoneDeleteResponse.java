package codesquard.app.milestone.dto.response;

public class MilestoneDeleteResponse {
	private final boolean success;

	private MilestoneDeleteResponse(boolean success) {
		this.success = success;
	}

	public static MilestoneDeleteResponse success() {
		return new MilestoneDeleteResponse(true);
	}

	public boolean isSuccess() {
		return success;
	}
}
