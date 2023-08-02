package codesquard.app.label.dto;

public class LabelSaveResponse {
	private final boolean success;
	private final Long id;

	private LabelSaveResponse(boolean success, Long id) {
		this.success = success;
		this.id = id;
	}

	public static LabelSaveResponse success(boolean success, Long id) {
		return new LabelSaveResponse(success, id);
	}

	public boolean isSuccess() {
		return success;
	}

	public Long getId() {
		return id;
	}
}
