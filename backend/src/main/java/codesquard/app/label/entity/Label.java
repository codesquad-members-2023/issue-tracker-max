package codesquard.app.label.entity;

public class Label {
	private Long id; // 등록번호
	private String name; // 이름
	private LabelColor color; // 글자색
	private String background; // 배경색
	private String description; // 설명

	public Label(Long id, String name, String color, String background, String description) {
		this.id = id;
		this.name = name;
		this.color = LabelColor.chooseColor(color);
		this.background = background;
		this.description = description;
	}

	public Label(String name, String color, String background, String description) {
		this.name = name;
		this.color = LabelColor.chooseColor(color);
		this.background = background;
		this.description = description;
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
