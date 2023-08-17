package codesquard.app.issue.mapper.response;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class IssueCount {

	private String status;
	private int count;

	public String getStatus() {
		return status;
	}

	public int getCount() {
		return count;
	}

	public boolean isStatus(String status) {
		return this.status.equals(status);
	}

}
