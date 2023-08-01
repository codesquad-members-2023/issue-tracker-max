package codesquard.app.label.dto;

import codesquard.app.label.entity.Label;

public class LabelSavedRequest {
	private String name;
	private String color;
	private String background;
	private String description;

	private LabelSavedRequest() {
	}

	public LabelSavedRequest(String name, String color, String background, String description) {
		this.name = name;
		this.color = color;
		this.background = background;
		this.description = description;
	}

	public static Label toEntity(LabelSavedRequest labelSavedRequest) {
		return new Label(labelSavedRequest.name, labelSavedRequest.color, labelSavedRequest.background,
			labelSavedRequest.description);
	}

	public String getName() {
		return name;
	}

	public String getColor() {
		return color;
	}

	public String getBackground() {
		return background;
	}

	public String getDescription() {
		return description;
	}
}
