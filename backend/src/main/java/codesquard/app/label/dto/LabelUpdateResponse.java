package codesquard.app.label.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LabelUpdateResponse {
	@JsonProperty("success")
	private final boolean success;

	private LabelUpdateResponse(boolean success) {
		this.success = success;
	}

	public static LabelUpdateResponse success() {
		return new LabelUpdateResponse(true);
	}
}
