package codesquard.app.label.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LabelSaveResponse {
	@JsonProperty("id")
	private final Long id;

	private LabelSaveResponse(final Long id) {
		this.id = id;
	}

	public static LabelSaveResponse success(final Long id) {
		return new LabelSaveResponse(id);
	}
}
