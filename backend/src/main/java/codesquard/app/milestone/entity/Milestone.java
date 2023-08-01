package codesquard.app.milestone.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@EqualsAndHashCode(of = "id")
@ToString
public class Milestone {
	@Getter
	private Long id; // 등록번호
	private boolean status; // true: open, false: close
	private LocalDateTime statusModifiedAt; // 상태(open/close)변경시간
	@Getter
	private String name; // 이름
	@Getter
	private String description; // 설명
	private LocalDateTime createdAt; // 생성시간
	private LocalDateTime modifiedAt; // 수정시간
	@Getter
	private LocalDate deadline; // 완료일

	public Milestone(Long id, String name, String description, LocalDate deadline) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.deadline = deadline;
	}

	public Milestone(String name, String description, LocalDate deadline) {
		this.name = name;
		this.description = description;
		this.deadline = deadline;
	}
}
