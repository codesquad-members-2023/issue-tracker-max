package codesquard.app.milestone.dto.request;

import java.time.LocalDate;

import javax.validation.constraints.Size;

import codesquard.app.milestone.entity.Milestone;

public class MilestoneCreateRequest {

	@Size(min = 1, max = 50, message = "제목은 1글자 이상, 50글자 이하여야 합니다.")
	private String name;
	@Size(max = 10000, message = "내용은 10000글자 이하여야 합니다.")
	private String description;
	private LocalDate deadline;

	public MilestoneCreateRequest() {
	}

	public MilestoneCreateRequest(String name, String description, LocalDate deadline) {
		this.name = name;
		this.description = description;
		this.deadline = deadline;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public LocalDate getDeadline() {
		return deadline;
	}

	public Milestone toEntity() {
		return new Milestone(name, description, deadline);
	}
}
