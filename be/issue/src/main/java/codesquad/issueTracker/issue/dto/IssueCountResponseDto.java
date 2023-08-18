package codesquad.issueTracker.issue.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class IssueCountResponseDto {
	private int openCount;
	private int closedCount;
	private int labelCount;
	private int milestoneCount;

	@Builder
	public IssueCountResponseDto(int openCount, int closedCount, int labelCount, int milestoneCount) {
		this.openCount = openCount;
		this.closedCount = closedCount;
		this.labelCount = labelCount;
		this.milestoneCount = milestoneCount;
	}

}
