package codesquard.app.label.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LabelUpdateResponse {
	@JsonProperty("success")
	private final boolean success;

	private LabelUpdateResponse(final boolean success) {
		this.success = success;
	}

	public static LabelUpdateResponse success() {
		return new LabelUpdateResponse(true);
	}
}
