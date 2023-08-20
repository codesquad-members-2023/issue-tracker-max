package codesquard.app.issue.mapper.response.filters.response;

import java.util.List;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class MultiFilterMilestones {

	private boolean multipleSelect = false;

	private List<MultiFilterMilestone> options;

	public MultiFilterMilestones(List<MultiFilterMilestone> options) {
		this.options = options;
	}

	public void addNoneOptionToMilestones(boolean selected) {
		options.add(0, new MultiFilterMilestone(0L, "마일스톤이 없는 이슈", selected));
	}

	public boolean isMultipleSelect() {
		return multipleSelect;
	}

	public List<MultiFilterMilestone> getOptions() {
		return options;
	}

}
