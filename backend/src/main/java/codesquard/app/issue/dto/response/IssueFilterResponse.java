package codesquard.app.issue.dto.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@RequiredArgsConstructor
public class IssueFilterResponse {

	// Input String
	@JsonProperty("input")
	private String input;

	// Filtered Issues
	@JsonProperty("issues")
	private List<IssuesResponse> issues;

	// Single Filters
	@JsonProperty("singleFilters")
	private SingleFilters singleFilters;

	// 열린 이슈, 내가 작성한 이슈, 나에게 할당된 이슈, 내가 댓글을 남긴 이슈, 닫힌 이슈
	// Multi Filters
	@JsonProperty("multiFilters")
	private MultiFilters multiFilters;

}
