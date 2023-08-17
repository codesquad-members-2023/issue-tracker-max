package codesquad.issueTracker.issue.domain;

import java.time.LocalDateTime;
import java.util.List;

import codesquad.issueTracker.label.domain.Label;
import codesquad.issueTracker.milestone.domain.Milestone;
import codesquad.issueTracker.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class IssueRead {

	private Long id;
	private String title;
	private Boolean isClosed;
	private LocalDateTime createdAt;
	private User user;
	private Milestone milestone;
	private List<Label> labels;

	@Builder
	public IssueRead(Long id, String title, Boolean isClosed, LocalDateTime createdAt, User user, Milestone milestone,
		List<Label> labels) {
		this.id = id;
		this.title = title;
		this.isClosed = isClosed;
		this.createdAt = createdAt;
		this.user = user;
		this.milestone = milestone;
		this.labels = labels;

	}
}
