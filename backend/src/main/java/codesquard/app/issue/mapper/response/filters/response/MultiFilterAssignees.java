package codesquard.app.issue.mapper.response.filters.response;

import java.util.List;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class MultiFilterAssignees {

	private boolean multipleSelect = false;

	private List<MultiFilterAssignee> options;

	public MultiFilterAssignees(List<MultiFilterAssignee> options) {
		this.options = options;
	}

	public void addNoneOptionToAssignee(boolean selected) {
		options.add(new MultiFilterAssignee(0L, "담당자가 없는 이슈", null, selected));
	}

	public boolean isMultipleSelect() {
		return multipleSelect;
	}

	public List<MultiFilterAssignee> getOptions() {
		return options;
	}

}
