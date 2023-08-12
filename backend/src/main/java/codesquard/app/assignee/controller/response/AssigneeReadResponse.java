package codesquard.app.assignee.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import codesquard.app.user.entity.User;

public class AssigneeReadResponse {
	@JsonProperty("id")
	private Long id;
	@JsonProperty("loginId")
	private String loginId;
	@JsonProperty("avatarUrl")
	private String avatarUrl;
	@JsonProperty("selected")
	private boolean selected;

	public AssigneeReadResponse() {
	}

	public AssigneeReadResponse(Long id, String loginId, String avatarUrl, boolean selected) {
		this.id = id;
		this.loginId = loginId;
		this.avatarUrl = avatarUrl;
		this.selected = selected;
	}

	public static AssigneeReadResponse from(User user) {
		return new AssigneeReadResponse(user.getId(), user.getLoginId(), user.getAvatarUrl(), false);
	}
}
