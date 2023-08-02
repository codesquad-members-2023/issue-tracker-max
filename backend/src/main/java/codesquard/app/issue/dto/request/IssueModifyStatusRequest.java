package codesquard.app.issue.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class IssueModifyStatusRequest {

	@JsonProperty("status")
	private String status;

	public String getStatus() {
		return status;
	}
}
