package codesquard.app.label.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import codesquard.app.label.entity.Label;
import codesquard.app.label.entity.LabelColor;

public class LabelsResponse {
	@JsonProperty("id")
	private Long id;
	@JsonProperty("name")
	private String name;
	@JsonProperty("color")
	private LabelColor color;
	@JsonProperty("background")
	private String background;
	@JsonProperty("description")
	private String description;

	private LabelsResponse(Long id, String name, LabelColor color, String background, String description) {
		this.id = id;
		this.name = name;
		this.color = color;
		this.background = background;
		this.description = description;
	}

	public static LabelsResponse fromEntity(final Label label) {
		return new LabelsResponse(label.getId(), label.getName(), label.getColor(), label.getBackground(),
			label.getDescription());
	}
}
