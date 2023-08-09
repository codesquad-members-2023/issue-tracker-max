package codesquad.issueTracker.milestone.domain;

import java.time.LocalDate;

import codesquad.issueTracker.global.exception.CustomException;
import codesquad.issueTracker.global.exception.ErrorCode;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Milestone {

	private Long id;
	private String name;
	private String description;
	private LocalDate doneDate;
	private Boolean isClosed;

	@Builder
	public Milestone(Long id, String name, String description, LocalDate doneDate, Boolean isClosed) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.doneDate = doneDate;
		this.isClosed = isClosed;
	}

	public void validateDate() {
		LocalDate currentDate = LocalDate.now();
		if (doneDate!=null &&doneDate.isBefore(currentDate)) {
			throw new CustomException(ErrorCode.INVALIDATE_DATE);
		}
	}
}