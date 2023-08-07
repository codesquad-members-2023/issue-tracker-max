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
import com.issuetrackermax.domain.assignee.AssigneeRepository;
import com.issuetrackermax.domain.comment.CommentRepository;
import com.issuetrackermax.domain.comment.entity.Comment;
import com.issuetrackermax.domain.history.HistoryRepository;
import com.issuetrackermax.domain.history.entity.History;
import com.issuetrackermax.domain.issue.IssueRepository;
import com.issuetrackermax.domain.issue.entity.IssueResultVO;
import com.issuetrackermax.domain.label.LabelRepository;
import com.issuetrackermax.domain.milestone.MilestoneRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class IssueService {
	private static final String OPEN_ISSUE = "open";
	private static final String CLOSED_ISSUE = "closed";
	private final IssueRepository issueRepository;
	private final CommentRepository commentRepository;
	private final HistoryRepository historyRepository;
	private final LabelRepository labelRepository;
	private final AssigneeRepository assigneeRepository;
	private final MilestoneRepository milestoneRepository;

	@Transactional
	public Long post(IssuePostRequest request, Long writerId) {
		Long issueId = issueRepository.save(request.toIssue(writerId));

		if (request.getAssigneeIds() != null) {
			applyAssignees(issueId, request.toAssignee());
		}

		if (request.getLabelIds() != null) {
			applyLabels(issueId, request.toLabel());
		}

		if (request.getContent() != null || request.getImageUrl() != null) {
			commentRepository.save(request.toComment(writerId));
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
		History history = historyRepository.findLatestByIssueId(id);
		List<Comment> comments = commentRepository.findByIssueId(id);

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
			.map(labelid -> LabelResponse.from(
				labelRepository.findbyId(Long.parseLong(labelid))))
			.collect(Collectors.toList());
	}

	@Transactional
	public void updateStatus(IssuesStatusRequest request) {
		int count;
		String status = request.getIssueStatus();
		List<Long> ids = request.getIssueIds();
		if (status.equals(OPEN_ISSUE)) {
			count = issueRepository.openByIds(ids);
		} else if (status.equals(CLOSED_ISSUE)) {
			count = issueRepository.closeByIds(ids);
		} else {
			throw new ApiException(IssueException.INVALID_ISSUE_STATUS);
		}

		// todo : 예외처리 Not Found 괜찮은지?
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

		if (!labelRepository.existByIds(request.getIds())) {
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

		if (!assigneeRepository.existByIds(request.getIds())) {
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

		if (!milestoneRepository.existById(milestoneId)) {
			throw new ApiException(MilestoneException.NOT_FOUND_MILESTONE);
		}

		issueRepository.applyMilestone(issueId, milestoneId);
	}
}

