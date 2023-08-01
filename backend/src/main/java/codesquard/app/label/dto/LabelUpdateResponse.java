package codesquard.app.label.dto;

public class LabelUpdateResponse {
	private final boolean success;

	private LabelUpdateResponse(boolean success) {
		this.success = success;
	}

	public static LabelUpdateResponse success(boolean success) {
		return new LabelUpdateResponse(success);
	}

	public boolean isSuccess() {
		return success;
	}
}
