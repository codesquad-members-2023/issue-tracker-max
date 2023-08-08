package codesquard.app.issue.dto.response;

import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonProperty;

import codesquard.app.user.entity.User;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class IssueUserResponse {

	@JsonProperty("id")
	private Long id;
	@JsonProperty("name")
	private String name;
	@JsonProperty("avatarUrl")
	private String avatarUrl;

	public static List<IssueUserResponse> from(List<User> assigneesBy) {
		return assigneesBy.stream()
			.map(user -> new IssueUserResponse(user.getId(), user.getLoginId(), user.getAvatarUrl()))
			.collect(Collectors.toUnmodifiableList());
	}
}
