package codesquard.app.milestone.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Milestone {
	private Long id; // 등록번호
	private boolean status; // true: open, false: close
	private LocalDateTime statusModifiedAt; // 상태(open/close)변경시간
	private final String name; // 이름
	private final String description; // 설명
	private LocalDateTime createdAt; // 생성시간
	private LocalDateTime modifiedAt; // 수정시간
	private final LocalDate deadline; // 완료일

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public LocalDate getDeadline() {
		return deadline;
	}
}
