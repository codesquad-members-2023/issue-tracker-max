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
	private Long writer;
	private Long milestone;
	private Long label;
	private Long assignee;

	@Builder
	public FilterRequest(Boolean issueIsOpen, Long writer, Long milestone, Long label, Long assignee) {
		this.issueIsOpen = issueIsOpen;
		this.writer = writer;
		this.milestone = milestone;
		this.label = label;
		this.assignee = assignee;
	}
}
