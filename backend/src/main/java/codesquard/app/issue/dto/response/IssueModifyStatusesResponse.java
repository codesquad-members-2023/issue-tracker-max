package codesquard.app.issue.dto.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class IssueModifyStatusesResponse {

	@JsonProperty("modifiedIssueId")
	private final List<Long> id;

	public static IssueModifyStatusesResponse success(List<Long> issueId) {
		return new IssueModifyStatusesResponse(issueId);
	}
}
