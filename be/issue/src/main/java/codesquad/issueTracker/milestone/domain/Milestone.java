package codesquad.issueTracker.milestone.domain;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Milestone {
	private Long id;
	private String name;
	private String description;
	private LocalDateTime doneDate;
	private Boolean isClosed;

	@Builder
	public Milestone(Long id, String name, String description, LocalDateTime doneDate, Boolean isClosed) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.doneDate = doneDate;
		this.isClosed = isClosed;
	}
}
