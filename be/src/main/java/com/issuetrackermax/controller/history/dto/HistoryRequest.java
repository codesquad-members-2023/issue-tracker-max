package com.issuetrackermax.controller.history.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class HistoryRequest {
	private String editor;
	private Long issueId;
	private Boolean issueIsOpen;

	@Builder
	public HistoryRequest(String editor, Long issueId, Boolean issueIsOpen) {
		this.editor = editor;
		this.issueId = issueId;
		this.issueIsOpen = issueIsOpen;
	}
}
