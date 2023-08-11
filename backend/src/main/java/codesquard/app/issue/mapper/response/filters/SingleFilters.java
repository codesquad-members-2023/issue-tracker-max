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
		// TODO: SingleFilter의 Enum에 RESPONSE라는 필드 추가(총 3개)하여 conditions를 ENUM으로 관리하게끔 하기

		this.singleFilters.add(new SingleFilter(OPENED_ID, SingleFilter.IS.OPENED.getName(), "is:opened",
			false));
		this.singleFilters.add(
			new SingleFilter(AUTHOR_ID, SingleFilter.ME.AUTHOR.getName(), "author:@me",
				false));
		this.singleFilters.add(
			new SingleFilter(ASSIGNEE_ID, SingleFilter.ME.ASSIGNEE.getName(), "assignee:@me",
				false));
		this.singleFilters.add(
			new SingleFilter(MENTIONS_ID, SingleFilter.ME.MENTIONS.getName(), "mentions:@me",
				false));
		this.singleFilters.add(
			new SingleFilter(CLOSED_ID, SingleFilter.IS.CLOSED.getName(), "is:closed",
				false));
	}

	public void changeBy(boolean selected, Long index) {
		singleFilters.get(Math.toIntExact(index)).changeSelected(selected);
	}

	public List<SingleFilter> getSingleFilters() {
		return singleFilters;
	}
}
