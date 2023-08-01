package com.issuetrackermax.controller.issue.dto.response;

import java.util.List;

import com.issuetrackermax.domain.filter.FilterResultVO;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class IssueResponse {

	private Long id;
	private Boolean isOpen;
	private String title;
	private HistoryResponse history;
	private List<LabelResponse> labels;
	private List<AssigneeResponse> assignees;
	private String writer;
	private String milestoneTitle;

	@Builder
	public IssueResponse(FilterResultVO resultVO) {
		this.id = resultVO.getId();
		this.isOpen = resultVO.getIsOpen();
		this.title = resultVO.getTitle();
		this.history = HistoryResponse.builder()
			.editor(resultVO.getEditor())
			.modifiedAt(resultVO.getModifiedAt())
			.build();
		this.labels = LabelResponse.convertToLabelResponseList(resultVO.getLabelIds(), resultVO.getLabelTitles());
		this.writer = resultVO.getWriter();
		this.assignees = AssigneeResponse.convertToAssigneeResponseList(resultVO.getAssigneeIds(),
			resultVO.getAssigneeNames());
		this.milestoneTitle = resultVO.getMilestoneTitle();
	}

}
