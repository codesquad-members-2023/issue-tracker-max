package codesquad.issueTracker.label.controller.dto;

import codesquad.issueTracker.label.domain.Label;
import lombok.Getter;

@Getter
public class LabelVO {
	private final Long id;
	private final String name;
	private final String textColor;
	private final String backgroundColor;
	private final String description;

	public LabelVO(Long id, String name, String textColor, String backgroundColor, String description) {
		this.id = id;
		this.name = name;
		this.textColor = textColor;
		this.backgroundColor = backgroundColor;
		this.description = description;
	}

	public static LabelVO from(Label label){
		return new LabelVO(label.getId(), label.getName(), label.getTextColor(), label.getBackgroundColor(),
			label.getDescription());
	}
}
