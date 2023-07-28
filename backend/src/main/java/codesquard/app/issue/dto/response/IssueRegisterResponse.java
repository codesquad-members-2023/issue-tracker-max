package codesquard.app.issue.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class IssueRegisterResponse {

	private final boolean success;
	private final Long id;
}
