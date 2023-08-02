package codesquard.app.issue.dto.response;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class IssueSaveResponse {

	private final boolean success;
	private final Long id;

	public boolean isSuccess() {
		return success;
	}

	public Long getId() {
		return id;
	}
}
