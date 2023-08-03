package codesquard.app.issue.dto.request;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class IssueModifyAssigneesRequest {

	@JsonProperty("assignees")
	private List<Long> assignees;

	public List<Long> getAssignees() {
		return assignees;
	}
}
