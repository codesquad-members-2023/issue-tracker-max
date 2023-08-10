package codesquard.app.issue.dto.response;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class IssueLabelsResponse {

	private Long id;
	private String name;
	private String color;
	private String background;

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getColor() {
		return color;
	}

	public String getBackground() {
		return background;
	}

}
