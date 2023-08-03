package codesquard.app.milestone.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MilestoneSaveResponse {
	@JsonProperty("success")
	private final boolean success;
	@JsonProperty("id")
	private final Long id;

	private MilestoneSaveResponse(final boolean success, final Long id) {
		this.success = success;
		this.id = id;
	}

	public static MilestoneSaveResponse success(final Long id) {
		return new MilestoneSaveResponse(true, id);
	}
}
