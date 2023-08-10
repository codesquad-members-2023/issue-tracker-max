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

	// Input String
	@JsonProperty("input")
	private String input;

	@JsonProperty("openedIssueCount")
	private Long openedIssueCount;

	@JsonProperty("closedIssueCount")
	private Long closedIssueCount;

	// Filtered Issues
	@JsonProperty("issues")
	private List<IssuesResponse> issues;

	// Single Filters
	// 열린 이슈, 내가 작성한 이슈, 나에게 할당된 이슈, 내가 댓글을 남긴 이슈, 닫힌 이슈
	@JsonProperty("singleFilters")
	private SingleFilters singleFilters;

	// Multi Filters
	@JsonProperty("multiFilters")
	private MultiFilters multiFilters;

}
