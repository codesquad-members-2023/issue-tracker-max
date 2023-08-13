package codesquad.issueTracker.label.domain;

import codesquad.issueTracker.label.dto.LabelRequestDto;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Label {
	private Long id;
	private String name;
	private String textColor;
	private String backgroundColor;
	private String description;

	@Builder
	public Label(Long id, String name, String textColor, String backgroundColor, String description) {
		this.id = id;
		this.name = name;
		this.textColor = textColor;
		this.backgroundColor = backgroundColor;
		this.description = description;
	}

	public static Label toEntity(LabelRequestDto labelRequestDto) {
		return Label.builder()
			.name(labelRequestDto.getName())
			.textColor(labelRequestDto.getTextColor())
			.backgroundColor(labelRequestDto.getBackgroundColor())
			.description(labelRequestDto.getDescription())
			.build();
	}

}
