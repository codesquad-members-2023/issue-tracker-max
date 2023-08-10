package codesquard.app.issue.dto.response;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class IssueAssigneeResponse {

	private Long id;
	private String name;
	private String avatarUrl;

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getAvatarUrl() {
		return avatarUrl;
	}

}
