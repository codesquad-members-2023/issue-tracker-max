package com.issuetrackermax.service.issue;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.issuetrackermax.controller.issue.dto.request.IssueApplyRequest;
import com.issuetrackermax.controller.issue.dto.request.IssuePostRequest;
import com.issuetrackermax.controller.issue.dto.request.IssueTitleRequest;
import com.issuetrackermax.controller.issue.dto.request.IssuesStatusRequest;
import com.issuetrackermax.controller.issue.dto.response.IssueDetailsResponse;
import com.issuetrackermax.domain.comment.CommentRepository;
import com.issuetrackermax.domain.comment.entity.Comment;
import com.issuetrackermax.domain.history.HistoryRepository;
import com.issuetrackermax.domain.history.entity.History;
import com.issuetrackermax.domain.issue.IssueRepository;
import com.issuetrackermax.domain.issue.entity.IssueResultVO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class IssueService {
	private final IssueRepository issueRepository;
	private final CommentRepository commentRepository;
	private final HistoryRepository historyRepository;
	private final String OPEN_ISSUE = "open";
	private final String CLOSED_ISSUE = "closed";

	// todo : 전체 예외 처리
	// todo : 이슈 등록 시 label, assignee 한 번에 등록하는 방법
	@Transactional
	public Long post(IssuePostRequest request) {
		Long issueId = issueRepository.save(request.toIssue());

		if (request.getAssigneeIds() != null) {
			applyAssignees(issueId, request.toAssignee());
		}

		if (request.getLabelIds() != null) {
			applyLabels(issueId, request.toLabel());
		}

		if (request.getContent() != null || request.getImageUrl() != null) {
			commentRepository.save(request.toComment());
		}
		return issueId;
	}

	@Transactional
	public void delete(Long id) {
		int count = issueRepository.deleteById(id);
		if (count == 0) {
			throw new IllegalArgumentException();
		}
	}

	@Transactional(readOnly = true)
	public IssueDetailsResponse show(Long id) {
		IssueResultVO issue = issueRepository.findById(id);
		History history = historyRepository.findLatestByIssueId(id);
		List<Comment> comments = commentRepository.findByIssueId(id);
		return new IssueDetailsResponse(issue, history, comments);
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
			throw new IllegalArgumentException();
		}

		if (count != ids.size()) {
			throw new IllegalArgumentException();
		}
	}

	@Transactional
	public void modifyTitle(Long issueId, IssueTitleRequest request) {
		int count = issueRepository.modifyTitle(issueId, request.getTitle());
		if (count != 1) {
			throw new IllegalArgumentException();
		}
	}

	/*
	 *  todo : label 있는지 검증
	 */
	@Transactional
	public void applyLabels(Long issueId, IssueApplyRequest request) {
		// 기존에 적용된 라벨 정보 삭제
		issueRepository.deleteAppliedLabels(issueId);

		// 새로운 라벨 정보 업데이트
		for (Long labelId : request.getIds()) {
			issueRepository.applyLabels(issueId, labelId);
		}
	}

	/*
	 *  todo : assignee 있는지 검증
	 */
	@Transactional
	public void applyAssignees(Long issueId, IssueApplyRequest request) {
		issueRepository.deleteAppliedAssignees(issueId);
		for (Long memberId : request.getIds()) {
			issueRepository.applyAssignees(issueId, memberId);
		}
	}

	// todo : milestone 있는지 검증
	@Transactional
	public void applyMilestone(Long issueId, Long milestoneId) {
		issueRepository.applyMilestone(issueId, milestoneId);
	}
}

