package codesquard.app.label.dto;

import codesquard.app.label.entity.Label;
import lombok.Getter;

@Getter
public class LabelRequest {
	private String name;
	private String color;
	private String background;
	private String description;

	private LabelRequest() {
	}

	public LabelRequest(String name, String color, String background, String description) {
		this.name = name;
		this.color = color;
		this.background = background;
		this.description = description;
	}

	public static Label toEntity(LabelRequest labelRequest) {
		return new Label(labelRequest.name, labelRequest.color, labelRequest.background,
			labelRequest.description);
	}
}
