package codesquard.app.label.dto;

import codesquard.app.label.entity.Label;
import lombok.Getter;

@Getter
public class LabelRequestDto {
	private String name;
	private String color;
	private String background;
	private String description;

	private LabelRequestDto() {
	}

	public LabelRequestDto(String name, String color, String background, String description) {
		this.name = name;
		this.color = color;
		this.background = background;
		this.description = description;
	}

	public static Label toEntity(LabelRequestDto labelRequestDto) {
		return new Label(labelRequestDto.name, labelRequestDto.color, labelRequestDto.background,
			labelRequestDto.description);
	}
}
