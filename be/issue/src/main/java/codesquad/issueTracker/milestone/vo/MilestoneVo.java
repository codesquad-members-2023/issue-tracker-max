package codesquad.issueTracker.milestone.vo;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MilestoneVo {
	private Long id;
	private String name;
	private String description;
	private LocalDate doneDate;
	private int issueOpenCount;
	private int issueClosedCount;

	@Builder
	public MilestoneVo(Long id, String name, String description, LocalDate doneDate, int issueOpenCount,
		int issueClosedCount) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.doneDate = doneDate;
		this.issueOpenCount = issueOpenCount;
		this.issueClosedCount = issueClosedCount;
	}
}
