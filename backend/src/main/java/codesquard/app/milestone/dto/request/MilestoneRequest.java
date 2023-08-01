package codesquard.app.milestone.dto.request;

import java.time.LocalDate;

import codesquard.app.milestone.entity.Milestone;

public class MilestoneRequest {
	private String name;
	private LocalDate deadline;
	private String description;

	private MilestoneRequest() {
	}

	public MilestoneRequest(String name, LocalDate deadline, String description) {
		this.name = name;
		this.deadline = deadline;
		this.description = description;
	}

	public static Milestone toEntity(MilestoneRequest milestoneRequest) {
		return new Milestone(milestoneRequest.name, milestoneRequest.description, milestoneRequest.deadline);
	}

	public String getName() {
		return name;
	}

	public LocalDate getDeadline() {
		return deadline;
	}

	public String getDescription() {
		return description;
	}
}
