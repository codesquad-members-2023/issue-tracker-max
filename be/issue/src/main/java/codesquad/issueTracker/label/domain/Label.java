package codesquad.issueTracker.label.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Label {
	private Long id;
	private String name;
	private String description;
	private String backgroundColor;
	private String textColor;

	@Builder
	public Label(Long id, String name, String description, String backgroundColor, String textColor) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.backgroundColor = backgroundColor;
		this.textColor = textColor;
	}
}
