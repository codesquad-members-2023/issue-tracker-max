package codesquard.app.issue.mapper.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import codesquard.app.issue.mapper.response.filters.MultiFilters;
import codesquard.app.issue.mapper.response.filters.SingleFilters;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@RequiredArgsConstructor
public class IssueFilterResponse {

	@JsonProperty("input")
	private String input;

	@JsonProperty("openedIssueCount")
	private Long openedIssueCount;

	@JsonProperty("closedIssueCount")
	private Long closedIssueCount;

	@JsonProperty("labelCount")
	private Long labelCount;

	@JsonProperty("milestoneCount")
	private Long milestoneCount;

	@JsonProperty("issues")
	private List<IssuesResponse> issues;

	@JsonProperty("singleFilters")
	private SingleFilters singleFilters;

	@JsonProperty("multiFilters")
	private MultiFilters multiFilters;

}
