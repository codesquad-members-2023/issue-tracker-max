package codesquard.app.label.dto;

public class LabelResponse {
	private final boolean success;
	private final Long id;

	private LabelResponse(boolean success, Long id) {
		this.success = success;
		this.id = id;
	}

	public static LabelResponse success(boolean success, Long id) {
		return new LabelResponse(success, id);
	}

	public boolean isSuccess() {
		return success;
	}

	public Long getId() {
		return id;
	}
}
