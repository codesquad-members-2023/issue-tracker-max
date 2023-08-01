package codesquard.app.milestone.dto.response;

public class MilestoneDeleteResponse {
	private final boolean success;

	private MilestoneDeleteResponse(boolean success) {
		this.success = success;
	}

	public static MilestoneDeleteResponse success(boolean success) {
		return new MilestoneDeleteResponse(success);
	}

	public boolean isSuccess() {
		return success;
	}
}
