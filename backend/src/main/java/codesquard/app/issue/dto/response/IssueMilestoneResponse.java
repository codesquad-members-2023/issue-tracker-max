package codesquard.app.issue.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class IssueMilestoneResponse {

	@JsonProperty("id")
	private Long id;
	@JsonProperty("name")
	private String name;
	@JsonProperty("issues")
	private IssueMilestoneCountResponse issues;

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}
}
