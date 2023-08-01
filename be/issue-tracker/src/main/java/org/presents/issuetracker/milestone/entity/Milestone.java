package org.presents.issuetracker.milestone.entity;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Milestone {
	private Long milestoneId;
	private String name;
	private LocalDateTime deadline;
	private String description;
	private String status;
}
