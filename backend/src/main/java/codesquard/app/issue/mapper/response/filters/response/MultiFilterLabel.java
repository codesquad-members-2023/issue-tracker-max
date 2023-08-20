package codesquard.app.issue.mapper.response.filters.response;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class MultiFilterLabel {

	private Long id;
	private String name;
	private String color;
	private String background;
	private boolean selected;

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

	public boolean isSelected() {
		return selected;
	}

}
