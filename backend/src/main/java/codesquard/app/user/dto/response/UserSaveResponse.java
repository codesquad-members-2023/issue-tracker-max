package codesquard.app.user.dto.response;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserSaveResponse {

	private final boolean success;
	private final Long id;

	public boolean isSuccess() {
		return success;
	}

	public Long getId() {
		return id;
	}
}
