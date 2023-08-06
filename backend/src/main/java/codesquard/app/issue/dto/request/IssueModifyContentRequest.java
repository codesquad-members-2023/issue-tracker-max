package codesquard.app.issue.dto.request;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class IssueModifyContentRequest {

	@JsonProperty("content")
	@Size(max = 10000, message = "내용은 10000글자 이하여야 합니다.")
	private String content;

	public String getContent() {
		return content;
	}
}
