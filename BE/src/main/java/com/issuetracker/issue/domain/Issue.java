package com.issuetracker.issue.domain;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Issue {

	private Long id;
	private String title;
	private String content;
	private boolean isOpen;
	private LocalDateTime createAt;
	private Long authorId;
	private Long milestoneId;

	@Builder
	private Issue(Long id, String title, String content, boolean isOpen, LocalDateTime createAt, Long authorId,
		Long milestoneId) {
		this.id = id;
		this.title = title;
		this.content = content;
		this.isOpen = isOpen;
		this.createAt = createAt;
		this.authorId = authorId;
		this.milestoneId = milestoneId;
	}

	public boolean getIsOpen() {
		return isOpen;
	}
}
