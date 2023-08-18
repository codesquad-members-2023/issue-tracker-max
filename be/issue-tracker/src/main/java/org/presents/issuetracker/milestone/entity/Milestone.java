package org.presents.issuetracker.milestone.entity;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Milestone {
	private Long id;
	private String name;
	private LocalDateTime deadline;
	private String description;
	private String status;

	@Builder
	private Milestone(Long id, String name, LocalDateTime deadline, String description, String status) {
		this.id = id;
		this.name = name;
		this.deadline = deadline;
		this.description = description;
		this.status = status;
	}

	private Milestone(String name, LocalDateTime deadline, String description) {
		this.name = name;
		this.deadline = deadline;
		this.description = description;
	}

	private Milestone(Long id, String name, LocalDateTime deadline, String description) {
		this.id = id;
		this.name = name;
		this.deadline = deadline;
		this.description = description;
	}

	public static Milestone of(String name, LocalDateTime deadline, String description) {
		return new Milestone(name, deadline, description);
	}

	public static Milestone of(Long id, String name, LocalDateTime deadline, String description, String status) {
		return new Milestone(id, name, deadline, description, status);
	}
}
