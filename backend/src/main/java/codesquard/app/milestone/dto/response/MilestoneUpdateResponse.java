package codesquard.app.milestone.dto.response;

public class MilestoneUpdateResponse {
	private final boolean success;

	private MilestoneUpdateResponse(boolean success) {
		this.success = success;
	}

	public static MilestoneUpdateResponse success(boolean success) {
		return new MilestoneUpdateResponse(success);
	}

	public boolean isSuccess() {
		return success;
	}
}
