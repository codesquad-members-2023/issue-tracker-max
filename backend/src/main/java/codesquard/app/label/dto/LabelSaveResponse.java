package codesquard.app.label.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LabelSaveResponse {
	@JsonProperty("success")
	private final boolean success;
	@JsonProperty("id")
	private final Long id;

	private LabelSaveResponse(boolean success, Long id) {
		this.success = success;
		this.id = id;
	}

	public static LabelSaveResponse success(Long id) {
		return new LabelSaveResponse(true, id);
	}
}
