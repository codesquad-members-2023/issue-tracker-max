package com.issuetrackermax.controller.filter.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FilterRequest {
	private Boolean issueIsOpen;
	private String writer;
	private Long milestone;
	private Long label;
	private String assignee;
	private String commentWriter;

	@Builder
	public FilterRequest(Boolean issueIsOpen, String writer, Long milestone, Long label, String assignee,
		String commentWriter) {
		this.issueIsOpen = issueIsOpen;
		this.writer = writer;
		this.milestone = milestone;
		this.label = label;
		this.assignee = assignee;
		this.commentWriter = commentWriter;
	}
}
