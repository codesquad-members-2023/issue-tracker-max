package codesquard.app.user_reaction.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserReactionDeleteResponse {

	@JsonProperty("deletedUserReactionId")
	private final Long id;

	public static UserReactionDeleteResponse success(Long userReactionId) {
		return new UserReactionDeleteResponse(userReactionId);
	}
}
