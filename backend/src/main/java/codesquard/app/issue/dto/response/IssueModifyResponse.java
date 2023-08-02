package codesquard.app.issue.dto.response;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class IssueModifyResponse {

	private final boolean success;

	public boolean isSuccess() {
		return success;
	}
}
