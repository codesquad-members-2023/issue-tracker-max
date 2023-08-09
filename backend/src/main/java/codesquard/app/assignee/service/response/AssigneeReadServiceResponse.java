package codesquard.app.assignee.service.response;

import codesquard.app.user.entity.User;

public class AssigneeReadServiceResponse {
	private Long id;
	private String loginId;
	private String avatarUrl;
	private boolean selected;

	public AssigneeReadServiceResponse(Long id, String loginId, String avatarUrl, boolean selected) {
		this.id = id;
		this.loginId = loginId;
		this.avatarUrl = avatarUrl;
		this.selected = selected;
	}

	public static AssigneeReadServiceResponse fromWithNoSelected(User user) {
		return new AssigneeReadServiceResponse(user.getId(), user.getLoginId(), user.getAvatarUrl(), false);
	}

	public AssigneeReadResponse toAssigneeReadResponse() {
		return new AssigneeReadResponse(id, loginId, avatarUrl, selected);
	}

}
