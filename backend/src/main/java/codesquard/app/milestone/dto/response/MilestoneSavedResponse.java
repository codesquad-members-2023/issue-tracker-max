package codesquard.app.milestone.dto.response;

public class MilestoneSavedResponse {
	private final boolean success;
	private final Long id;

	private MilestoneSavedResponse(boolean success, Long id) {
		this.success = success;
		this.id = id;
	}

	public static MilestoneSavedResponse success(boolean success, Long id) {
		return new MilestoneSavedResponse(success, id);
	}

	public boolean isSuccess() {
		return success;
	}

	public Long getId() {
		return id;
	}
}
