package codesquard.app.issue.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class IssueDeleteResponse {

	@JsonProperty("success")
	private final boolean success;

	public static IssueDeleteResponse success() {
		return new IssueDeleteResponse(true);
	}
}
