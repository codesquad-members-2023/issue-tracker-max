package codesquard.app.issue.dto.response;

public class IssueRegisterResponse {

	private final boolean success;
	private final Long id;

	public IssueRegisterResponse(boolean success, Long id) {
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
