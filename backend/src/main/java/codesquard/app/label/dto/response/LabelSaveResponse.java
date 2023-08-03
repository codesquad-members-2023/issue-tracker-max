package codesquard.app.label.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LabelSaveResponse {
	@JsonProperty("success")
	private final boolean success;
	@JsonProperty("id")
	private final Long id;

	private LabelSaveResponse(final boolean success, final Long id) {
		this.success = success;
		this.id = id;
	}

	public static LabelSaveResponse success(final Long id) {
		return new LabelSaveResponse(true, id);
	}
}
