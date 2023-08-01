package codesquard.app.label.entity;

public class Label {
	private Long id; // 등록번호
	private String name; // 이름
	private LabelColor color; // 글자색
	private String background; // 배경색
	private String description; // 설명

	public Label(String name, String color, String background, String description) {
		this.name = name;
		chooseColor(color);
		this.background = background;
		this.description = description;
	}

	private void chooseColor(String color) {
		if (color.equalsIgnoreCase(LabelColor.DARK_STRING)) {
			this.color = LabelColor.DARK;
			return;
		}

		this.color = LabelColor.LIGHT;
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
