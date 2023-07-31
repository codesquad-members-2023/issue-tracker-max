package codesquard.app.milestone.dto.response;

public class MilestoneCreateResponse {

	private final boolean success;
	private final Long id;

	public MilestoneCreateResponse(boolean success, Long id) {
		this.success = success;
		this.id = id;
	}

	public boolean isSuccess() {
		return success;
	}

	public Long getId() {
		return id;
	}
}
