package com.issuetrackermax.domain.history.entity;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
public class History {
	private Long id;
	private Long editor;
	private Long issueId;
	private Boolean issueIsOpen;
	private LocalDateTime modifiedAt;

	@Builder
	public History(Long id, Long editor, Long issueId, Boolean issueIsOpen, LocalDateTime modifiedAt) {
		this.id = id;
		this.editor = editor;
		this.issueId = issueId;
		this.issueIsOpen = issueIsOpen;
		this.modifiedAt = modifiedAt;
	}
}
