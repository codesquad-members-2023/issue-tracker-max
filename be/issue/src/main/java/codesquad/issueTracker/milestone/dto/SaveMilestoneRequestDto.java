package codesquad.issueTracker.milestone.dto;

import java.time.LocalDate;

import javax.validation.constraints.Pattern;

import codesquad.issueTracker.milestone.domain.Milestone;
import lombok.Getter;

@Getter
public class SaveMilestoneRequestDto {

	private String name;
	private String description;
	@Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "yyyy-mm-dd 형식으로 입력해주세요")
	private String doneDate;
	private Boolean isClosed;

	public static Milestone toEntity(SaveMilestoneRequestDto request) {
		return Milestone.builder()
			.name(request.getName())
			.description(request.getDescription())
			.doneDate(request.getDoneDate()!=null?LocalDate.parse(request.getDoneDate()):null)
			.isClosed(request.getIsClosed())
			.build();
	}

}
