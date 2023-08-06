package codesquard.app.issue.dto.request;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class IssueModifyLabelsRequest {

	@JsonProperty("labels")
	private List<Long> labels;

	public List<Long> getLabels() {
		return labels;
	}
}
