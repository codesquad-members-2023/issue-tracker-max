package codesquard.app.issue.mapper.response;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class IssueLabelResponse {

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
