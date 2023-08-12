package codesquard.app.issue.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class IssueSaveResponse {

	@JsonProperty("savedIssueId")
	private final Long id;

	public static IssueSaveResponse success(Long issueId) {
		return new IssueSaveResponse(issueId);
	}
}
