package codesquard.app.milestone.dto.request;

import java.time.LocalDate;

import codesquard.app.milestone.entity.Milestone;

public class MilestoneSavedRequest {
	private String name;
	private LocalDate deadline;
	private String description;

	private MilestoneSavedRequest() {
	}

	public MilestoneSavedRequest(String name, LocalDate deadline, String description) {
		this.name = name;
		this.deadline = deadline;
		this.description = description;
	}

	public static Milestone toEntity(MilestoneSavedRequest milestoneSavedRequest) {
		return new Milestone(milestoneSavedRequest.name, milestoneSavedRequest.description, milestoneSavedRequest.deadline);
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
