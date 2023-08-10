package codesquard.app.issue.mapper.response.filters;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@RequiredArgsConstructor
public class SingleFilters {
	public enum IS {
		OPENED("is:opened", "열린 이슈"),
		CLOSED("is:closed", "닫힌 이슈");

		private final String type;
		private final String name;

		IS(String type, String name) {
			this.type = type;
			this.name = name;
		}

		public String getType() {
			return type;
		}

		public String getName() {
			return name;
		}
	}

	public static final String IS_OPENED = "is:opened";
	public static final String NAME_OPENED = "열린 이슈";
	public static final String IS_CLOSED = "is:closed";
	public static final String NAME_CLOSED = "닫힌 이슈";

	public enum ME {
		AUTHOR("author:@me", "내가 작성한 이슈"),
		ASSIGNEE("assignee:@me", "나에게 할당된 이슈"),
		MENTIONS("mentions:@me", "내가 댓글을 남긴 이슈");

		private final String type;
		private final String name;

		ME(String type, String name) {
			this.type = type;
			this.name = name;
		}

		public String getType() {
			return type;
		}

		public String getName() {
			return name;
		}
	}

	public static final String AUTHOR_ME = "author:@me";
	public static final String NAME_AUTHOR = "내가 작성한 이슈";
	public static final String ASSIGNEE_ME = "assignee:@me";
	public static final String NAME_ASSIGNEE = "나에게 할당된 이슈";
	public static final String MENTIONS_ME = "mentions:@me";
	public static final String NAME_MENTIONS = "내가 댓글을 남긴 이슈";

	@JsonProperty("id")
	private Long id;
	// "내가 댓글을 남긴 이슈"
	@JsonProperty("name")
	private String name;
	// ["is:closed", memtions:@me"]
	@JsonProperty("conditions")
	private List<String> conditions;
	// false
	// conditions에 지정된 형식이 아니라면 false
	// 지정된 형식이 하나라도 존재한다면 true
	@JsonProperty("selected")
	private boolean selected;

}
