package com.issuetrackermax.controller.filter.dto.response;

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
	private WriterResponse writer;
	private MilestoneResponse milestone;

	@Builder
	public IssueResponse(FilterResultVO resultVO, List<LabelResponse> labels) {
		this.id = resultVO.getId();
		this.isOpen = resultVO.getIsOpen();
		this.title = resultVO.getTitle();
		this.history = HistoryResponse.builder()
			.editor(resultVO.getEditor())
			.modifiedAt(resultVO.getModifiedAt())
			.build();
		this.labels = labels;
		this.writer = WriterResponse.builder()
			.id(resultVO.getWriterId())
			.name(resultVO.getWriter())
			.imageUrl(resultVO.getWriterImageUrl())
			.build();
		this.assignees = AssigneeResponse.convertToAssigneeResponseList(resultVO.getAssigneeIds(),
			resultVO.getAssigneeNames());
		setMilestone(resultVO.getMilestoneId(), resultVO.getMilestoneTitle());
	}

	private void setMilestone(Long id, String title) {
		if (title == null) {
			this.milestone = null;
		} else {
			this.milestone = MilestoneResponse.builder()
				.id(id)
				.title(title)
				.build();
		}
	}

}
