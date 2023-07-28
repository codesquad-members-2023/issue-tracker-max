package codesquard.app.label.entity;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@EqualsAndHashCode(of = "id")
@ToString
public class Label {

	private Long id; // 등록번호
	private String name; // 이름
	private String color; // 글자색
	private String background; // 배경색
	private String description; // 설명
}
