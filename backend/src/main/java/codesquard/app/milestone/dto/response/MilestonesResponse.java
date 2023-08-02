package codesquard.app.milestone.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

import codesquard.app.milestone.entity.Milestone;
import codesquard.app.milestone.entity.MilestoneStatus;

public class MilestonesResponse {
	private Long id; // 등록번호
	private MilestoneStatus status; // OPEN, CLOSE
	private String name; // 이름
	private String description; // 설명
	private LocalDateTime createdAt; // 생성 시간
	private LocalDateTime modifiedAt; // 수정 시간
	private LocalDate deadline; // 완료일
	private Map<String, Long> issues; // 이슈 카운트

	private MilestonesResponse(Long id, MilestoneStatus status, String name, String description,
		LocalDateTime createdAt, LocalDateTime modifiedAt, LocalDate deadline, Map<String, Long> issues) {
		this.id = id;
		this.status = status;
		this.name = name;
		this.description = description;
		this.createdAt = createdAt;
		this.modifiedAt = modifiedAt;
		this.deadline = deadline;
		this.issues = issues;
	}

	public static MilestonesResponse fromEntity(final Milestone milestone, final Map<String, Long> issues) {
		return new MilestonesResponse(milestone.getId(), milestone.getStatus(), milestone.getName(),
			milestone.getDescription(), milestone.getCreatedAt(), milestone.getModifiedAt(), milestone.getDeadline(),
			issues);
	}

	public Long getId() {
		return id;
	}

	public MilestoneStatus getStatus() {
		return status;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public LocalDateTime getModifiedAt() {
		return modifiedAt;
	}

	public LocalDate getDeadline() {
		return deadline;
	}

	public Map<String, Long> getIssues() {
		return issues;
	}
}
