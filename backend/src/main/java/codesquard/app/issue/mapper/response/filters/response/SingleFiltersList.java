package codesquard.app.issue.mapper.response.filters.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import codesquard.app.issue.mapper.response.filters.SingleFilters;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class SingleFiltersList {

	private List<SingleFilters> singleFilters;

	public List<SingleFilters> getSingleFilters() {
		return singleFilters;
	}
}
