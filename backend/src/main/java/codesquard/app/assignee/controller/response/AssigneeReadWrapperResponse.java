package codesquard.app.assignee.controller.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import codesquard.app.assignee.service.response.AssigneeReadResponse;

public class AssigneeReadWrapperResponse {
	@JsonProperty("assignees")
	private List<AssigneeReadResponse> assignees;

	public AssigneeReadWrapperResponse() {
	}

	public AssigneeReadWrapperResponse(List<AssigneeReadResponse> assignees) {
		this.assignees = assignees;
	}
}
