package com.issuetrackermax.service.issue;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.issuetrackermax.common.exception.ApiException;
import com.issuetrackermax.common.exception.AssigneeCustomException;
import com.issuetrackermax.common.exception.domain.IssueException;
import com.issuetrackermax.common.exception.domain.LabelException;
import com.issuetrackermax.common.exception.domain.MilestoneException;
import com.issuetrackermax.controller.filter.dto.response.LabelResponse;
import com.issuetrackermax.controller.issue.dto.request.IssueApplyRequest;
import com.issuetrackermax.controller.issue.dto.request.IssuePostRequest;
import com.issuetrackermax.controller.issue.dto.request.IssueTitleRequest;
import com.issuetrackermax.controller.issue.dto.request.IssuesStatusRequest;
import com.issuetrackermax.controller.issue.dto.response.IssueDetailsResponse;
import com.issuetrackermax.domain.comment.entity.Comment;
import com.issuetrackermax.domain.history.entity.History;
import com.issuetrackermax.domain.issue.IssueRepository;
import com.issuetrackermax.domain.issue.entity.IssueResultVO;
import com.issuetrackermax.service.assignee.AssigneeService;
import com.issuetrackermax.service.comment.CommentService;
import com.issuetrackermax.service.history.HistoryService;
import com.issuetrackermax.service.label.LabelService;
import com.issuetrackermax.service.milestone.MilestoneService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class IssueService {
	private static final String OPEN_ISSUE = "open";
	private static final String CLOSED_ISSUE = "closed";
	private final IssueRepository issueRepository;
	private final CommentService commentService;
	private final HistoryService historyService;
	private final LabelService labelService;
	private final AssigneeService assigneeService;
	private final MilestoneService milestoneService;

	@Transactional
	public Long post(IssuePostRequest request, Long writerId) {
		validatePostRequest(request);
		Long issueId = issueRepository.save(request.toIssue(writerId));

		if (request.getAssigneeIds() != null) {
			applyAssignees(issueId, request.toAssignee());
		}

		if (request.getLabelIds() != null) {
			applyLabels(issueId, request.toLabel());
		}

		if (request.getContent() != null || request.getImageUrl() != null) {
			commentService.save(request.toComment(writerId));
		}
		return issueId;
	}

	@Transactional
	public void delete(Long id) {
		int count = issueRepository.deleteById(id);
		if (count == 0) {
			throw new ApiException(IssueException.NOT_FOUND_ISSUE);
		}
	}

	@Transactional(readOnly = true)
	public IssueDetailsResponse show(Long id) {
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

	private List<LabelResponse> getLabelResponse(String[] labelIds) {
		return Arrays.stream(labelIds)
			.map(labelId -> LabelResponse.from(
				labelService.findById(Long.parseLong(labelId))))
			.collect(Collectors.toList());
	}

	@Transactional
	public void updateStatus(IssuesStatusRequest request) {
		String status = request.getIssueStatus();
		List<Long> ids = request.getIssueIds();
		if (status.equals(OPEN_ISSUE)) {
			openIssue(ids);
		} else if (status.equals(CLOSED_ISSUE)) {
			closeIssue(ids);
		} else {
			throw new ApiException(IssueException.INVALID_ISSUE_STATUS);
		}
	}

	public void openIssue(List<Long> ids) {
		int count = issueRepository.openByIds(ids);
		if (count != ids.size()) {
			throw new ApiException(IssueException.NOT_FOUND_ISSUE);
		}
	}

	public void closeIssue(List<Long> ids) {
		int count = issueRepository.closeByIds(ids);
		if (count != ids.size()) {
			throw new ApiException(IssueException.NOT_FOUND_ISSUE);
		}
	}

	@Transactional
	public void modifyTitle(Long issueId, IssueTitleRequest request) {
		int count = issueRepository.modifyTitle(issueId, request.getTitle());
		if (count != 1) {
			throw new ApiException(IssueException.NOT_FOUND_ISSUE);
		}
	}

	@Transactional
	public void applyLabels(Long issueId, IssueApplyRequest request) {
		if (!issueRepository.existById(issueId)) {
			throw new ApiException(IssueException.NOT_FOUND_ISSUE);
		}

		if (!labelService.existByIds(request.getIds())) {
			throw new ApiException(LabelException.NOT_FOUND_LABEL);
		}

		issueRepository.deleteAppliedLabels(issueId);
		for (Long labelId : request.getIds()) {
			issueRepository.applyLabels(issueId, labelId);
		}
	}

	@Transactional
	public void applyAssignees(Long issueId, IssueApplyRequest request) {
		if (!issueRepository.existById(issueId)) {
			throw new ApiException(IssueException.NOT_FOUND_ISSUE);
		}

		if (!assigneeService.existByIds(request.getIds())) {
			throw new ApiException(AssigneeCustomException.NOT_FOUND_ASSIGNEE);
		}

		issueRepository.deleteAppliedAssignees(issueId);
		for (Long memberId : request.getIds()) {
			issueRepository.applyAssignees(issueId, memberId);
		}
	}

	@Transactional
	public void applyMilestone(Long issueId, Long milestoneId) {
		if (!issueRepository.existById(issueId)) {
			throw new ApiException(IssueException.NOT_FOUND_ISSUE);
		}

		if (!milestoneService.existById(milestoneId)) {
			throw new ApiException(MilestoneException.NOT_FOUND_MILESTONE);
		}

		issueRepository.applyMilestone(issueId, milestoneId);
	}

	public void validatePostRequest(IssuePostRequest request) {
		Long milestoneId = request.getMilestoneId();
		List<Long> labelIds = request.getLabelIds();
		List<Long> assigneeIds = request.getAssigneeIds();

		if (milestoneId != null) {
			milestoneService.existById(milestoneId);
		}

		if (labelIds != null) {
			labelService.existByIds(labelIds);
		}

		if (assigneeIds != null) {
			assigneeService.existByIds(assigneeIds);
		}
	}
}
