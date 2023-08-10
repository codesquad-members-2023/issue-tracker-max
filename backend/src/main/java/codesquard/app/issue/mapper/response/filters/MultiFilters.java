package codesquard.app.issue.mapper.response.filters;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import codesquard.app.issue.dto.response.IssueLabelResponse;
import codesquard.app.issue.dto.response.IssueMilestoneResponse;
import codesquard.app.issue.dto.response.IssueUserResponse;
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

	private List<MultiFiltersAuthors> authors;

	private List<MultiFiltersLabels> labels;

	private List<MultiFiltersMilestones> milestones;

	public List<MultiFiltersAssignees> getAssignees() {
		return assignees;
	}

	public List<MultiFiltersAuthors> getAuthors() {
		return authors;
	}

	public List<MultiFiltersLabels> getLabels() {
		return labels;
	}

	public List<MultiFiltersMilestones> getMilestones() {
		return milestones;
	}

}
