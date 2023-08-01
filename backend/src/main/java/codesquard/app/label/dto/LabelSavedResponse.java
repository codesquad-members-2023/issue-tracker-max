package codesquard.app.label.dto;

public class LabelSavedResponse {
	private final boolean success;
	private final Long id;

	private LabelSavedResponse(boolean success, Long id) {
		this.success = success;
		this.id = id;
	}

	public static LabelSavedResponse success(boolean success, Long id) {
		return new LabelSavedResponse(success, id);
	}

	public boolean isSuccess() {
		return success;
	}

	public Long getId() {
		return id;
	}
}
