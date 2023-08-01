package codesquard.app.milestone.dto.request;

import java.time.LocalDate;

import codesquard.app.milestone.entity.Milestone;
import lombok.Getter;

@Getter
public class MilestoneRequestDto {
	private String name;
	private LocalDate deadline;
	private String description;

	private MilestoneRequestDto() {
	}

	public MilestoneRequestDto(String name, LocalDate deadline, String description) {
		this.name = name;
		this.deadline = deadline;
		this.description = description;
	}

	public static Milestone toEntity(MilestoneRequestDto milestoneRequestDto) {
		return new Milestone(milestoneRequestDto.name, milestoneRequestDto.description, milestoneRequestDto.deadline);
	}
}
