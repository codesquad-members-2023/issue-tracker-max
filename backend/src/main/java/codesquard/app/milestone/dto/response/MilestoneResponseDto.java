package codesquard.app.milestone.dto.response;

import lombok.Getter;

@Getter
public class MilestoneResponseDto {
	private final boolean success;
	private final Long id;

	private MilestoneResponseDto(boolean success, Long id) {
		this.success = success;
		this.id = id;
	}

	public static MilestoneResponseDto success(boolean success, Long id) {
		return new MilestoneResponseDto(success, id);
	}
}
