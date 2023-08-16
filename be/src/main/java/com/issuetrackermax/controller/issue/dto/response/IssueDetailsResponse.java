package com.issuetrackermax.controller.issue.dto.response;

import java.util.List;

import com.issuetrackermax.controller.filter.dto.response.AssigneeResponse;
import com.issuetrackermax.controller.filter.dto.response.HistoryResponse;
import com.issuetrackermax.controller.filter.dto.response.LabelResponse;
import com.issuetrackermax.controller.filter.dto.response.MilestoneResponse;
import com.issuetrackermax.controller.filter.dto.response.WriterResponse;
import com.issuetrackermax.domain.comment.entity.CommentMemberVO;
import com.issuetrackermax.domain.history.entity.History;
import com.issuetrackermax.domain.issue.entity.IssueResultVO;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class IssueDetailsResponse {
	private Long id;
	private Boolean isOpen;
	private String title;
	private List<LabelResponse> labels;
	private List<AssigneeResponse> assignees;
	private WriterResponse writer;
	private MilestoneResponse milestone;
	private HistoryResponse history;
	private List<CommentMemberVO> comments;

	@Builder
	public IssueDetailsResponse(IssueResultVO resultVO, History history, List<CommentMemberVO> comments,
		List<LabelResponse> labels) {
		this.id = resultVO.getId();
		this.isOpen = resultVO.getIsOpen();
		this.title = resultVO.getTitle();
		this.labels = labels;
		this.assignees = AssigneeResponse.convertToAssigneeResponseList(resultVO.getAssigneeIds(),
			resultVO.getAssigneeNames());
		this.writer = WriterResponse.builder()
			.id(resultVO.getWriterId())
			.name(resultVO.getWriter())
			.build();
		this.milestone = MilestoneResponse.builder()
			.id(resultVO.getMilestoneId())
			.title(resultVO.getMilestoneTitle())
			.build();
		this.history = HistoryResponse.builder()
			.editor(history.getEditor())
			.modifiedAt(history.getModifiedAt())
			.build();
		this.comments = comments;
	}
}
