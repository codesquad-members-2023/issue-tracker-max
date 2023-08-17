package codesquard.app.issue.mapper.response.filters.response;

import java.util.List;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class MultiFilterAuthors {

	private boolean multipleSelect = false;

	private List<MultiFilterAuthor> options;

	public MultiFilterAuthors(List<MultiFilterAuthor> options) {
		this.options = options;
	}

	public boolean isMultipleSelect() {
		return multipleSelect;
	}

	public List<MultiFilterAuthor> getOptions() {
		return options;
	}

}
