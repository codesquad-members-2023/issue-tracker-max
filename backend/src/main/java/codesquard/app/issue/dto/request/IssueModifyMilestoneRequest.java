package codesquard.app.issue.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class IssueModifyMilestoneRequest {

	@JsonProperty("milestone")
	private Long milestone;

	public Long getMilestone() {
		return milestone;
	}
}
