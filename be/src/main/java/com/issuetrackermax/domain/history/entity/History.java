package com.issuetrackermax.domain.history.entity;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
public class History {
	private Long id;
	private Long modifier;
	private Long issueId;
	private Boolean issueIsOpen;
	private LocalDateTime modifiedAt;

	@Builder
	public History(Long id, Long modifier, Long issueId, Boolean issueIsOpen, LocalDateTime modifiedAt) {
		this.id = id;
		this.modifier = modifier;
		this.issueId = issueId;
		this.issueIsOpen = issueIsOpen;
		this.modifiedAt = modifiedAt;
	}
}
