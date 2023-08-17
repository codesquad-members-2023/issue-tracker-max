package codesquard.app.issue.dto.request;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class IssueModifyStatusesRequest {

	@JsonProperty("issues")
	private List<Long> issues;
	@JsonProperty("status")
	private String status;

	public List<Long> getIssues() {
		return issues;
	}

	public String getStatus() {
		return status;
	}
}
