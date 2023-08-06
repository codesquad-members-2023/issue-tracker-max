package codesquard.app.issue.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class IssueModifyResponse {

	@JsonProperty("success")
	private final boolean success;

	public static IssueModifyResponse success() {
		return new IssueModifyResponse(true);
	}
}
