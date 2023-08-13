package com.issuetrackermax.domain.history.entity;

import java.time.LocalDateTime;

import com.issuetrackermax.controller.history.dto.HistoryRequest;

import lombok.Builder;
import lombok.Getter;

@Getter
public class History {
	private Long id;
	private String editor;
	private Long issueId;
	private Boolean issueIsOpen;
	private LocalDateTime modifiedAt;

	@Builder
	public History(Long id, String editor, Long issueId, Boolean issueIsOpen, LocalDateTime modifiedAt) {
		this.id = id;
		this.editor = editor;
		this.issueId = issueId;
		this.issueIsOpen = issueIsOpen;
		this.modifiedAt = modifiedAt;
	}

	public static History from(HistoryRequest historyRequest) {
		return History.builder()
			.issueId(historyRequest.getIssueId())
			.issueIsOpen(historyRequest.getIssueIsOpen())
			.editor(historyRequest.getEditor())
			.build();
	}
}
