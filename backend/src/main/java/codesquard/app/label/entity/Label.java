package codesquard.app.label.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@EqualsAndHashCode(of = "id")
@ToString
@Getter
@AllArgsConstructor
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
}
