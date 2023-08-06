package codesquard.app.issue.dto.response;

import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonProperty;

import codesquard.app.label.entity.Label;
import codesquard.app.label.entity.LabelColor;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class IssueLabelResponse {

	@JsonProperty("id")
	private Long id;
	@JsonProperty("name")
	private String name;
	@JsonProperty("background")
	private String background;
	@JsonProperty("color")
	private LabelColor color;

	public static List<IssueLabelResponse> from(List<Label> labels) {
		return labels.stream()
			.map(label -> new IssueLabelResponse(label.getId(), label.getName(), label.getBackground(),
				label.getColor()))
			.collect(Collectors.toUnmodifiableList());
	}

	public String getName() {
		return name;
	}

	public String getBackground() {
		return background;
	}

	public LabelColor getColor() {
		return color;
	}
}
