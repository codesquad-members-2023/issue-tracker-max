package codesquard.app.user_reaction.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserReactionSaveResponse {

	@JsonProperty("savedUserReactionId")
	private final Long id;

	public static UserReactionSaveResponse success(Long userReactionId) {
		return new UserReactionSaveResponse(userReactionId);
	}
}
