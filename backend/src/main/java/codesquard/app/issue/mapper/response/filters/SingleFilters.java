package codesquard.app.issue.mapper.response.filters;

import java.util.ArrayList;
import java.util.List;

import codesquard.app.issue.mapper.response.filters.response.SingleFilter;
import lombok.NoArgsConstructor;

public class SingleFilters {

	private final List<SingleFilter> singleFilters = new ArrayList<>();

	public static final Long OPENED_ID = 1L;
	public static final Long AUTHOR_ID = 2L;
	public static final Long ASSIGNEE_ID = 3L;
	public static final Long MENTIONS_ID = 4L;
	public static final Long CLOSED_ID = 5L;

	public SingleFilters() {
		this.singleFilters.add(
			new SingleFilter(OPENED_ID, SingleFilter.IS.OPENED.getName(), SingleFilter.IS.OPENED.getResponse(),
			false)
		);
		this.singleFilters.add(
			new SingleFilter(AUTHOR_ID, SingleFilter.ME.AUTHOR.getName(), SingleFilter.ME.AUTHOR.getResponse(),
				false)
		);
		this.singleFilters.add(
			new SingleFilter(ASSIGNEE_ID, SingleFilter.ME.ASSIGNEE.getName(), SingleFilter.ME.ASSIGNEE.getResponse(),
				false)
		);
		this.singleFilters.add(
			new SingleFilter(MENTIONS_ID, SingleFilter.ME.MENTIONS.getName(), SingleFilter.ME.MENTIONS.getResponse(),
				false)
		);
		this.singleFilters.add(
			new SingleFilter(CLOSED_ID, SingleFilter.IS.CLOSED.getName(), SingleFilter.IS.CLOSED.getResponse(),
				false)
		);
	}

	public void changeBy(boolean selected, Long index) {
		singleFilters.get(Math.toIntExact(index)).changeSelected(selected);
	}

	public List<SingleFilter> getSingleFilters() {
		return singleFilters;
	}
}
