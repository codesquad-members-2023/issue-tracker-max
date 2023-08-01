package com.issuetrackermax.controller.issue.dto.response;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
public class HistoryResponse {
	private String editor;
	private LocalDateTime modifiedAt;

	@Builder
	public HistoryResponse(String editor, LocalDateTime modifiedAt) {
		this.editor = editor;
		this.modifiedAt = modifiedAt;
	}
}
