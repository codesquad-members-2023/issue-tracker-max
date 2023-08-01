package codesquard.app.label.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import codesquard.app.label.entity.Label;
import codesquard.app.label.entity.LabelColor;

public class LabelSavedRequest {
	@NotNull(message = "제목 입력은 필수입니다.")
	@Size(min = 1, max = 20, message = "제목은 1글자 이상, 20글자 이하여야 합니다.")
	private String name;
	@NotNull(message = "글자색 입력은 필수입니다.")
	private String color;
	@NotNull(message = "배경색 입력은 필수입니다.")
	@Pattern(regexp = "^#[0-9A-Fa-f]{6}$", message = "올바른 배경색 형식이어야 합니다. (예: #RRGGBB)")
	private String background;
	@Size(min = 1, max = 10000, message = "내용은 1글자 이상, 10000글자 이하여야 합니다.")
	private String description;

	private LabelSavedRequest() {
	}

	public LabelSavedRequest(String name, String color, String background, String description) {
		this.name = name;
		validateColor(color);
		this.background = background;
		this.description = description;
	}

	private void validateColor(String color) {
		if (!color.equalsIgnoreCase(LabelColor.DARK_STRING) && !color.equalsIgnoreCase(LabelColor.LIGHT_STRING)) {
			throw new RuntimeException("임시");
		}

		this.color = color;
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
