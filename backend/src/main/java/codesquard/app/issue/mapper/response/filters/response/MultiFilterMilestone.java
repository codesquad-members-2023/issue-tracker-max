package codesquard.app.issue.mapper.response.filters.response;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class MultiFilterMilestone {

	private Long id;
	private String name;
	private boolean selected;

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public boolean isSelected() {
		return selected;
	}

}
