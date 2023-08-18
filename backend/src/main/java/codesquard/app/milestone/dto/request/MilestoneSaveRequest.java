package codesquard.app.milestone.dto.request;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

import codesquard.app.milestone.entity.Milestone;

public class MilestoneSaveRequest {
	@NotNull(message = "제목 입력은 필수입니다.")
	@Size(min = 1, max = 50, message = "제목은 1글자 이상, 50글자 이하여야 합니다.")
	@JsonProperty("name")
	private String name;
	@JsonProperty("deadline")
	private LocalDate deadline;
	@Size(min = 0, max = 10000, message = "내용은 0글자 이상, 10000글자 이하여야 합니다.")
	@JsonProperty("description")
	private String description;

	private MilestoneSaveRequest() {
	}

	public MilestoneSaveRequest(final String name, final LocalDate deadline, final String description) {
		this.name = name;
		this.deadline = deadline;
		this.description = description;
	}

	public static Milestone toEntity(final MilestoneSaveRequest milestoneSaveRequest, final Long userId) {
		return new Milestone(milestoneSaveRequest.name, milestoneSaveRequest.description,
			milestoneSaveRequest.deadline, userId);
	}
}
