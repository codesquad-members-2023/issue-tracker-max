package codesquard.app.issue.dto.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class userReactionResponse {

	@JsonProperty("unicode")
	private final String unicode;
	@JsonProperty("users")
	private final List<String> users;
	@JsonProperty("selectedUserReactionId")
	private final Long selected;
}
