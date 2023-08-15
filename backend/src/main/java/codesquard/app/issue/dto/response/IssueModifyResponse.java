package codesquard.app.issue.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class IssueModifyResponse {

	@JsonProperty("modifiedIssueId")
	private final Long id;

	public static IssueModifyResponse success(Long issueId) {
		return new IssueModifyResponse(issueId);
	}
}
