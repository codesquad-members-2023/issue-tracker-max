package codesquard.app.issue.mapper.response;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class IssueMilestoneResponse {

	private Long id;
	private String name;

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

}
