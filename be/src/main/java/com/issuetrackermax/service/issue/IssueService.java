package com.issuetrackermax.service.issue;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.issuetrackermax.common.exception.ApiException;
import com.issuetrackermax.common.exception.domain.AssigneeException;
import com.issuetrackermax.common.exception.domain.IssueException;
import com.issuetrackermax.common.exception.domain.LabelException;
import com.issuetrackermax.common.exception.domain.MilestoneException;
import com.issuetrackermax.controller.filter.dto.response.LabelResponse;
import com.issuetrackermax.controller.issue.dto.request.IssueApplyRequest;
import com.issuetrackermax.controller.issue.dto.request.IssuePostRequest;
import com.issuetrackermax.controller.issue.dto.request.IssueTitleRequest;
import com.issuetrackermax.controller.issue.dto.request.IssuesStatusRequest;
import com.issuetrackermax.controller.issue.dto.response.IssueDetailsResponse;
import com.issuetrackermax.controller.issue.dto.response.IssuePostResponse;
import com.issuetrackermax.domain.comment.entity.Comment;
import com.issuetrackermax.domain.history.entity.History;
import com.issuetrackermax.domain.issue.IssueCommentRepository;
import com.issuetrackermax.domain.issue.IssueLabelRepository;
import com.issuetrackermax.domain.issue.IssueRepository;
import com.issuetrackermax.domain.issue.entity.IssueResultVO;
import com.issuetrackermax.domain.issue.entity.IssueWithComment;
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
	private final IssueLabelRepository issueLabelRepository;
	private final IssueCommentRepository issueCommentRepository;
	private final CommentService commentService;
	private final HistoryService historyService;
	private final LabelService labelService;
	private final AssigneeService assigneeService;
	private final MilestoneService milestoneService;

	@Transactional
	public IssuePostResponse post(IssuePostRequest request, Long writerId) {
		Long issueId = issueRepository.save(request.toIssue(writerId));

		if (request.getAssigneeIds() != null) {
			applyAssignees(issueId, request.toAssignee());
		}

		if (request.getLabelIds() != null) {
			applyLabels(issueId, request.toLabel());
		}

		if (request.getContent() != null || request.getImageUrl() != null) {
			Long commentId = commentService.save(request.toComment(writerId));
			issueCommentRepository.save(IssueWithComment.builder().issueId(issueId).commentId(commentId).build());
		}
		return IssuePostResponse.from(issueId);
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

	@Transactional
	public void delete(Long id) {
		int count = issueRepository.deleteById(id);
		if (count == 0) {
			throw new ApiException(IssueException.NOT_FOUND_ISSUE);
		}
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

		issueLabelRepository.deleteAppliedLabels(issueId);
		for (Long labelId : request.getIds()) {
			issueLabelRepository.applyLabels(issueId, labelId);
		}
	}

	@Transactional
	public void applyAssignees(Long issueId, IssueApplyRequest request) {
		if (!issueRepository.existById(issueId)) {
			throw new ApiException(IssueException.NOT_FOUND_ISSUE);
		}

		if (!assigneeService.existByIds(request.getIds())) {
			throw new ApiException(AssigneeException.NOT_FOUND_ASSIGNEE);
		}

		assigneeService.deleteAppliedAssignees(issueId);
		for (Long memberId : request.getIds()) {
			assigneeService.applyAssigneesToIssue(issueId, memberId);
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

		milestoneService.applyMilestoneToIssue(issueId, milestoneId);
	}
}
