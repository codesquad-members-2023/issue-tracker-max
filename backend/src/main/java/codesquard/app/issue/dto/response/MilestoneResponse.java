package codesquard.app.issue.dto.response;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class MilestoneResponse {

	private Long id;
	private String name;

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

}
