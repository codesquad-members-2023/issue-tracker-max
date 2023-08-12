package codesquard.app.issue.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class IssueDeleteResponse {

	@JsonProperty("deletedIssueId")
	private final Long id;

	public static IssueDeleteResponse success(Long issueId) {
		return new IssueDeleteResponse(issueId);
	}
}
