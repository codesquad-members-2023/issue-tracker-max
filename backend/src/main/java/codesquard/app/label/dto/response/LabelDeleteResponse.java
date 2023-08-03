package codesquard.app.label.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LabelDeleteResponse {
	@JsonProperty("success")
	private final boolean success;

	private LabelDeleteResponse(final boolean success) {
		this.success = success;
	}

	public static LabelDeleteResponse success() {
		return new LabelDeleteResponse(true);
	}
}
