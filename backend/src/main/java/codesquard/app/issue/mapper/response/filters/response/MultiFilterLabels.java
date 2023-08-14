package codesquard.app.issue.mapper.response.filters.response;

import java.util.List;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class MultiFilterLabels {

	private boolean multipleSelect = true;

	private List<MultiFilterLabel> options;

	public MultiFilterLabels(List<MultiFilterLabel> options) {
		this.options = options;
	}

	public void addNoneOptionToLabels(boolean selected) {
		options.add(new MultiFilterLabel(0L, "라벨이 없는 이슈", null, null, selected));
	}

	public boolean isMultipleSelect() {
		return multipleSelect;
	}

	public List<MultiFilterLabel> getOptions() {
		return options;
	}

}
