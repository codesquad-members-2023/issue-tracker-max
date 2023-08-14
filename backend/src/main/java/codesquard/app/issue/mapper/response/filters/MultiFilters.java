package codesquard.app.issue.mapper.response.filters;

import codesquard.app.issue.mapper.response.filters.response.MultiFilterAssignees;
import codesquard.app.issue.mapper.response.filters.response.MultiFilterAuthors;
import codesquard.app.issue.mapper.response.filters.response.MultiFilterLabels;
import codesquard.app.issue.mapper.response.filters.response.MultiFilterMilestones;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@RequiredArgsConstructor
public class MultiFilters {

	private MultiFilterAssignees assignees;

	private MultiFilterLabels labels;

	private MultiFilterMilestones milestones;

	private MultiFilterAuthors authors;

	public MultiFilterAssignees getAssignees() {
		return assignees;
	}

	public MultiFilterLabels getLabels() {
		return labels;
	}

	public MultiFilterMilestones getMilestones() {
		return milestones;
	}

	public MultiFilterAuthors getAuthors() {
		return authors;
	}

}
