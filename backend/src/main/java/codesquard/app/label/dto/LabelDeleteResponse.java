package codesquard.app.label.dto;

public class LabelDeleteResponse {
	private final boolean success;

	private LabelDeleteResponse(boolean success) {
		this.success = success;
	}

	public static LabelDeleteResponse success(boolean success) {
		return new LabelDeleteResponse(success);
	}

	public boolean isSuccess() {
		return success;
	}
}
