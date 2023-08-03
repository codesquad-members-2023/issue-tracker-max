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

	public Milestone(String status) {
		chooseStatus(status);
	}

	public Milestone(String name, String description, LocalDate deadline) {
		this.name = name;
		this.description = description;
		this.deadline = deadline;
	}

	public Milestone(Long id, String status, LocalDateTime createdAt, LocalDateTime modifiedAt, String name,
		String description, LocalDate deadline) {
		this.id = id;
		chooseStatus(status);
		this.createdAt = createdAt;
		this.modifiedAt = modifiedAt;
		this.name = name;
		this.description = description;
		this.deadline = deadline;
	}

	private void chooseStatus(String status) {
		if (status.equalsIgnoreCase("OPENED")) {
			this.status = MilestoneStatus.OPENED;
			return;
		}

		this.status = MilestoneStatus.CLOSED;
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
