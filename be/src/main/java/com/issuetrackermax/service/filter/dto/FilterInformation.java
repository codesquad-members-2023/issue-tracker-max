package com.issuetrackermax.service.filter.dto;

import com.issuetrackermax.controller.filter.dto.request.FilterRequest;

import lombok.Builder;
import lombok.Getter;

@Getter
public class FilterInformation {
	private Boolean issueIsOpen;
	private Long writer;
	private Long milestone;
	private Long label;
	private Long assignee;
	private Long commentWriter;

	@Builder
	public FilterInformation(Boolean issueIsOpen, Long writer, Long milestone, Long label, Long assignee,
		Long commentWriter) {
		this.issueIsOpen = issueIsOpen;
		this.writer = writer;
		this.milestone = milestone;
		this.label = label;
		this.assignee = assignee;
		this.commentWriter = commentWriter;
	}

	public static FilterInformation from(FilterRequest filterRequest, Long memberId) {
		return FilterInformation.builder()
			.issueIsOpen(filterRequest.getIssueIsOpen())
			.writer(convertToId(filterRequest.getWriter(), memberId))
			.milestone(filterRequest.getMilestone())
			.label(filterRequest.getLabel())
			.assignee(convertToId(filterRequest.getAssignee(), memberId))
			.commentWriter(convertToId(filterRequest.getCommentWriter(), memberId))
			.build();
	}

	private static Long convertToId(String key, Long memberId) {
		if (key == null) {
			return null;
		}
		if (key.equals("me")) {
			return memberId;
		}
		try {
			return Long.parseLong(key);
		} catch (NumberFormatException e) {
			return null;
		}
	}

}
