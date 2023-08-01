package codesquard.app.milestone.dto.response;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MilestoneSaveResponse {

	private final boolean success;
	private final Long id;

	public boolean isSuccess() {
		return success;
	}

	public Long getId() {
		return id;
	}
}
