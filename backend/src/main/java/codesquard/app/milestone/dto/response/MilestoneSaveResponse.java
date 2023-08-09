package codesquard.app.milestone.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MilestoneSaveResponse {
	@JsonProperty("id")
	private final Long id;

	private MilestoneSaveResponse(final Long id) {
		this.id = id;
	}

	public static MilestoneSaveResponse success(final Long id) {
		return new MilestoneSaveResponse(id);
	}
}
