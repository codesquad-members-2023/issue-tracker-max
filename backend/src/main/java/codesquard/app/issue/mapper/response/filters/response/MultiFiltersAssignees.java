package codesquard.app.issue.mapper.response.filters.response;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class MultiFiltersAssignees {

	private Long id;
	private String name;
	private String avatarUrl;
	private boolean selected;

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getAvatarUrl() {
		return avatarUrl;
	}

	public boolean isSelected() {
		return selected;
	}

}
