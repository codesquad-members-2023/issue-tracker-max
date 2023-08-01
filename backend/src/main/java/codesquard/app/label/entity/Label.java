package codesquard.app.label.entity;

public class Label {
	private Long id; // 등록번호
	private String name; // 이름
	private String color; // 글자색
	private String background; // 배경색
	private String description; // 설명

	public Label(String name, String color, String background, String description) {
		this.name = name;
		this.color = color;
		this.background = background;
		this.description = description;
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
