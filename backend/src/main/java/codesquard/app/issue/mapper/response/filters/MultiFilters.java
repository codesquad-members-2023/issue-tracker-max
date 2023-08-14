package codesquard.app.issue.mapper.response.filters;

import java.util.List;

import codesquard.app.issue.mapper.response.filters.response.MultiFiltersAssignees;
import codesquard.app.issue.mapper.response.filters.response.MultiFiltersAuthors;
import codesquard.app.issue.mapper.response.filters.response.MultiFiltersLabels;
import codesquard.app.issue.mapper.response.filters.response.MultiFiltersMilestones;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@RequiredArgsConstructor
public class MultiFilters {

	private List<MultiFiltersAssignees> assignees;

	private List<MultiFiltersLabels> labels;

	private List<MultiFiltersMilestones> milestones;

	private List<MultiFiltersAuthors> authors;

	public void addNoneOptionToAssignee(boolean selected) {
		assignees.add(new MultiFiltersAssignees(0L, "담당자가 없는 이슈", null, selected));
	}

	public void addNoneOptionToLabels(boolean selected) {
		labels.add(new MultiFiltersLabels(0L, "라벨이 없는 이슈", null, null, selected));
	}

	public void addNoneOptionToMilestones(boolean selected) {
		milestones.add(new MultiFiltersMilestones(0L, "마일스톤이 없는 이슈", selected));
	}

	public List<MultiFiltersAssignees> getAssignees() {
		return assignees;
	}

	public List<MultiFiltersLabels> getLabels() {
		return labels;
	}

	public List<MultiFiltersMilestones> getMilestones() {
		return milestones;
	}

	public List<MultiFiltersAuthors> getAuthors() {
		return authors;
	}

}
