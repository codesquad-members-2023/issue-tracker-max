package codesquad.issueTracker.label.controller.dto;

import lombok.Getter;

@Getter
public class CreateLabelResponseDto {
	private Long labelId;

	public CreateLabelResponseDto(Long labelId) {
		this.labelId = labelId;
	}

	public static CreateLabelResponseDto from(Long id){
		return new CreateLabelResponseDto(id);
	}
}
