package codesquard.app.label.entity;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

public class Label {
	private Long id; // 등록번호
	private String name; // 이름
	private LabelColor color; // 글자색
	private String background; // 배경색
	private String description; // 설명

	public Label(final Long id, final String name, final String color, final String background,
		final String description) {
		this.id = id;
		this.name = name;
		this.color = LabelColor.chooseColor(color);
		this.background = background;
		this.description = description;
	}

	public Label(Long id, String name, String color, String background) {
		this.id = id;
		this.name = name;
		this.color = LabelColor.chooseColor(color);
		this.background = background;
	}

	public Label(final String name, final String color, final String background, final String description) {
		this.name = name;
		this.color = LabelColor.chooseColor(color);
		this.background = background;
		this.description = description;
	}

	public static SqlParameterSource makeParam(final Label label) {
		return new MapSqlParameterSource()
			.addValue("name", label.name)
			.addValue("color", label.color.getNameToLowerCase())
			.addValue("background", label.background)
			.addValue("description", label.description);
	}

	public static SqlParameterSource makeParam(final Long labelId, final Label label) {
		return new MapSqlParameterSource()
			.addValue("name", label.name)
			.addValue("color", label.color.getNameToLowerCase())
			.addValue("background", label.background)
			.addValue("description", label.description)
			.addValue("id", labelId);
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public LabelColor getColor() {
		return color;
	}

	public String getBackground() {
		return background;
	}

	public String getDescription() {
		return description;
	}
}
