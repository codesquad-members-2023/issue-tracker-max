package codesquard.app.issue.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class IssueMilestoneCountResponse {

	@JsonProperty("openedIssueCount")
	private int openedIssueCount;
	@JsonProperty("closedIssueCount")
	private int closedIssueCount;
}
