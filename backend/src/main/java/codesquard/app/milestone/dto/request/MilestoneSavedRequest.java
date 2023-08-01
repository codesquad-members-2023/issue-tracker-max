package codesquard.app.milestone.dto.request;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import codesquard.app.milestone.entity.Milestone;

public class MilestoneSavedRequest {
	@NotNull(message = "제목 입력은 필수입니다.")
	@Size(min = 1, max = 50, message = "제목은 1글자 이상, 50글자 이하여야 합니다.")
	private String name;
	private LocalDate deadline;
	@Size(min = 1, max = 10000, message = "내용은 1글자 이상, 10000글자 이하여야 합니다.")
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
