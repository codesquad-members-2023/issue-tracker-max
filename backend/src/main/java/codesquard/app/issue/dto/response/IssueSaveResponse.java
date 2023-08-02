package codesquard.app.issue.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class IssueSaveResponse {

	@JsonProperty("success")
	private final boolean success;
	@JsonProperty("id")
	private final Long id;

	public static IssueSaveResponse success(Long id) {
		return new IssueSaveResponse(true, id);
	}
}
