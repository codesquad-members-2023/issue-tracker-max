package codesquard.app.issue.dto.response;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class IssueRegisterResponse {

	private final boolean success;
	private final Long id;
}
