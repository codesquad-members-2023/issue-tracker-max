package codesquad.issueTracker.issue.domain;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class IssueSearch {

	private Boolean isClosed;
	private List<Long> assigneeIds;
	private List<Long> labelIds;
	private Long milestoneId;
	private Long userId;
	private Long commentAuthorId;

	@Builder
	public IssueSearch(Boolean isClosed, List<Long> assigneeIds, List<Long> labelIds, Long milestoneId, Long userId,
		Long commentAuthorId) {
		this.isClosed = isClosed;
		this.assigneeIds = assigneeIds;
		this.labelIds = labelIds;
		this.milestoneId = milestoneId;
		this.userId = userId;
		this.commentAuthorId = commentAuthorId;
	}

}
