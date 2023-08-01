package codesquard.app.milestone.dto.response;

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

	public boolean isSuccess() {
		return success;
	}

	public Long getId() {
		return id;
	}
}
