package codesquard.app.label.dto;

import lombok.Getter;

@Getter
public class LabelResponseDto {
	private final boolean success;
	private final Long id;

	private LabelResponseDto(boolean success, Long id) {
		this.success = success;
		this.id = id;
	}

	public static LabelResponseDto success(boolean success, Long id) {
		return new LabelResponseDto(success, id);
	}
}
