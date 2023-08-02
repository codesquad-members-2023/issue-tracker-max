package codesquard.app.milestone.dto.response;

public class MilestoneSaveResponse {
	private final boolean success;
	private final Long id;

	private MilestoneSaveResponse(boolean success, Long id) {
		this.success = success;
		this.id = id;
	}

	public static MilestoneSaveResponse success(Long id) {
		return new MilestoneSaveResponse(true, id);
	}

	public boolean isSuccess() {
		return success;
	}

	public Long getId() {
		return id;
	}
}
