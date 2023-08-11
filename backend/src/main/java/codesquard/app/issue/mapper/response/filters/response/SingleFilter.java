package codesquard.app.issue.mapper.response.filters.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@RequiredArgsConstructor
public class SingleFilter {
	public enum IS {
		OPENED("opened", "열린 이슈", "is:opened"),
		CLOSED("closed", "닫힌 이슈", "is:closed");

		private final String type;
		private final String name;
		private final String response;

		IS(String type, String name, String response) {
			this.type = type;
			this.name = name;
			this.response = response;
		}

		public String getType() {
			return type;
		}

		public String getName() {
			return name;
		}

		public String getResponse() {
			return response;
		}
	}

	public enum ME {
		AUTHOR("@me", "내가 작성한 이슈", "author:@me"),
		ASSIGNEE("@me", "나에게 할당된 이슈", "assignee:@me"),
		MENTIONS("@me", "내가 댓글을 남긴 이슈", "mentions:@me");

		private final String type;
		private final String name;
		private final String response;

		ME(String type, String name, String response) {
			this.type = type;
			this.name = name;
			this.response = response;
		}

		public String getType() {
			return type;
		}

		public String getName() {
			return name;
		}

		public String getResponse() {
			return response;
		}
	}

	@JsonProperty("id")
	private Long id;
	// "내가 댓글을 남긴 이슈"
	@JsonProperty("name")
	private String name;
	// ["is:closed", memtions:@me"]
	@JsonProperty("conditions")
	private String conditions;
	// false
	// conditions에 지정된 형식이 아니라면 false
	// 지정된 형식이 하나라도 존재한다면 true
	@JsonProperty("selected")
	private boolean selected;

	public void changeSelected(boolean selected) {
		this.selected = selected;
	}
}
