package com.issuetrackermax.service.issue;

import static com.issuetrackermax.domain.issue.IssueStatus.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.issuetrackermax.controller.filter.dto.response.LabelResponse;
import com.issuetrackermax.controller.issue.dto.request.IssueApplyRequest;
import com.issuetrackermax.controller.issue.dto.request.IssuePostRequest;
import com.issuetrackermax.controller.issue.dto.request.IssueTitleRequest;
import com.issuetrackermax.controller.issue.dto.request.IssuesStatusRequest;
import com.issuetrackermax.controller.issue.dto.response.IssueDetailsResponse;
import com.issuetrackermax.controller.issue.dto.response.IssuePostResponse;
import com.issuetrackermax.domain.comment.entity.Comment;
import com.issuetrackermax.domain.history.entity.History;
import com.issuetrackermax.domain.issue.IssueLabelRepository;
import com.issuetrackermax.domain.issue.IssueRepository;
import com.issuetrackermax.domain.issue.IssueValidator;
import com.issuetrackermax.domain.issue.entity.IssueResultVO;
import com.issuetrackermax.domain.label.LabelValidator;
import com.issuetrackermax.domain.member.MemberValidator;
import com.issuetrackermax.domain.milestone.MilestoneValidator;
import com.issuetrackermax.service.assignee.AssigneeService;
import com.issuetrackermax.service.comment.CommentService;
import com.issuetrackermax.service.history.HistoryService;
import com.issuetrackermax.service.label.LabelService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class IssueService {

	private final CommentService commentService;
	private final HistoryService historyService;
	private final LabelService labelService;
	private final AssigneeService assigneeService;
	private final MemberValidator memberValidator;
	private final IssueValidator issueValidator;
	private final LabelValidator labelValidator;
	private final MilestoneValidator milestoneValidator;
	private final IssueRepository issueRepository;
	private final IssueLabelRepository issueLabelRepository;

	@Transactional
	public IssuePostResponse post(IssuePostRequest request, Long writerId) {
		memberValidator.existById(writerId);
		Long issueId = issueRepository.save(request.toIssue(writerId));
		applyAssignees(issueId, request.toAssignee());
		applyLabels(issueId, request.toLabel());

		if (request.getContent() != null) {
			commentService.save(request.toCommentCreateRequest(issueId), writerId);
		}
		return IssuePostResponse.from(issueId);
	}

	@Transactional(readOnly = true)
	public IssueDetailsResponse show(Long id) {
		issueValidator.existById(id);
		IssueResultVO issueResultVO = issueRepository.findIssueDetailsById(id);
		History history = historyService.findLatestByIssueId(id);
		List<Comment> comments = commentService.findByIssueId(id);

		String[] labelIds = issueResultVO.getLabelIds().split(",");
		List<LabelResponse> labels = getLabelResponse(labelIds);
		return IssueDetailsResponse
			.builder()
			.resultVO(issueResultVO)
			.history(history)
			.comments(comments)
			.labels(labels)
			.build();
	}

	@Transactional
	public void delete(Long id) {
		issueRepository.deleteById(id);
	}

	@Transactional
	public void updateStatus(IssuesStatusRequest request, Long memberId) {
		memberValidator.existById(memberId);
		String status = request.getIssueStatus();
		List<Long> ids = request.getIssueIds();
		issueValidator.validStatus(status);
		issueValidator.existByIds(ids);
		if (status.equals(OPEN_ISSUE.getStatus())) {
			openIssue(ids);
		} else if (status.equals(CLOSED_ISSUE.getStatus())) {
			closeIssue(ids);
		}
	}

	@Transactional
	public void applyLabels(Long issueId, IssueApplyRequest request) {
		if (request.getIds() == null) {
			return;
		}
		issueValidator.existById(issueId);
		labelValidator.existByIds(request.getIds());
		issueLabelRepository.deleteAppliedLabels(issueId);
		for (Long labelId : request.getIds()) {
			issueLabelRepository.applyLabels(issueId, labelId);
		}
	}

	@Transactional
	public void applyAssignees(Long issueId, IssueApplyRequest request) {
		if (request.getIds() == null) {
			return;
		}
		assigneeService.deleteAppliedAssignees(issueId);
		for (Long memberId : request.getIds()) {
			assigneeService.applyAssigneesToIssue(issueId, memberId);
		}
	}

	@Transactional
	public void applyMilestone(Long issueId, Long milestoneId) {
		issueValidator.existById(issueId);
		milestoneValidator.existById(milestoneId);
		issueRepository.applyMilestoneToIssue(issueId, milestoneId);
	}

	@Transactional
	public void modifyTitle(Long issueId, IssueTitleRequest request) {
		issueValidator.existById(issueId);
		issueRepository.modifyTitle(issueId, request.getTitle());
	}

	private List<LabelResponse> getLabelResponse(String[] labelIds) {
		return Arrays.stream(labelIds)
			.map(labelId -> LabelResponse.from(
				labelService.findById(Long.parseLong(labelId))))
			.collect(Collectors.toList());
	}

	public void openIssue(List<Long> ids) {
		issueRepository.openByIds(ids);
	}

	public void closeIssue(List<Long> ids) {
		issueRepository.closeByIds(ids);
	}
}
