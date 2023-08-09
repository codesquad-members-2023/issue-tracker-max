package kr.codesquad.issuetracker.presentation.response;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MilestoneResponse {

	private Integer milestoneId;
	private String milestoneName;
	@JsonInclude(value = JsonInclude.Include.NON_NULL)
	private String description;
	@JsonInclude(value = JsonInclude.Include.NON_NULL)
	private LocalDateTime dueDate;
	private Integer openIssueCount;
	private Integer closedIssueCount;

	public MilestoneResponse(Integer milestoneId, String milestoneName, String description, Timestamp dueDate,
		Integer openIssueCount, Integer closedIssueCount) {
		this.milestoneId = milestoneId;
		this.milestoneName = milestoneName;
		this.description = description;
		if (dueDate != null) {
			this.dueDate = dueDate.toLocalDateTime();
		}
		this.openIssueCount = openIssueCount;
		this.closedIssueCount = closedIssueCount;
	}

	public MilestoneResponse(Integer milestoneId, String milestoneName, Integer openIssueCount,
		Integer closedIssueCount) {
		this(milestoneId, milestoneName, null, null, openIssueCount, closedIssueCount);
	}
}
