package codesquard.app.milestone.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Milestone {
	private Long id; // 등록번호
	private MilestoneStatus status; // OPEN, CLOSE
	private LocalDateTime statusModifiedAt; // 상태(open/close) 변경 시간
	private LocalDateTime createdAt; // 생성 시간
	private LocalDateTime modifiedAt; // 수정 시간
	private String name; // 이름
	private String description; // 설명
	private LocalDate deadline; // 완료일

	public Milestone(final String name, final String description, final LocalDate deadline) {
		this.name = name;
		this.description = description;
		this.deadline = deadline;
	}

	public Milestone(final Long id, final LocalDateTime createdAt, final LocalDateTime modifiedAt,
		final String name,
		final String description, final LocalDate deadline) {
		this.id = id;
		this.createdAt = createdAt;
		this.modifiedAt = modifiedAt;
		this.name = name;
		this.description = description;
		this.deadline = deadline;
	}

	public Long getId() {
		return id;
	}

	public MilestoneStatus getStatus() {
		return status;
	}

	public LocalDateTime getStatusModifiedAt() {
		return statusModifiedAt;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public LocalDateTime getModifiedAt() {
		return modifiedAt;
	}

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
