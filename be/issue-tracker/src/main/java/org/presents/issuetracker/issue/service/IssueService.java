package org.presents.issuetracker.issue.service;

import java.util.List;
import java.util.stream.Collectors;

import org.presents.issuetracker.issue.dto.request.IssueCreateRequest;
import org.presents.issuetracker.issue.dto.request.IssueSearchParam;
import org.presents.issuetracker.issue.dto.response.IssueDetailResponse;
import org.presents.issuetracker.issue.dto.response.IssueSearch;
import org.presents.issuetracker.issue.dto.response.IssueSearchResponse;
import org.presents.issuetracker.issue.entity.Assignee;
import org.presents.issuetracker.issue.entity.Issue;
import org.presents.issuetracker.issue.entity.IssueLabel;
import org.presents.issuetracker.issue.entity.vo.IssueSearchVo;
import org.presents.issuetracker.issue.mapper.IssueMapper;
import org.presents.issuetracker.issue.repository.IssueRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IssueService {
	private final IssueRepository issueRepository;
	private final IssueMapper issueMapper;

	@Transactional(rollbackFor = Exception.class)
	public Long create(IssueCreateRequest issueCreateRequest) {
		Long savedIssueId = issueRepository.save(
			Issue.builder()
				.title(issueCreateRequest.getTitle())
				.authorId(issueCreateRequest.getAuthorId())
				.contents(issueCreateRequest.getContents())
				.build()
		);

		addAssignees(issueCreateRequest.getAssigneeIds(), savedIssueId);
		addLabels(issueCreateRequest.getLabelIds(), savedIssueId);
		setMilestone(issueCreateRequest.getMilestoneId(), savedIssueId);

		return savedIssueId;
	}

	private void addAssignees(List<Long> assigneeIds, Long issueId) {
		issueRepository.addAssignees(
			assigneeIds.stream()
				.map(assigneeId ->
					Assignee.builder()
						.issueId(issueId)
						.userId(assigneeId)
						.build()
				)
				.collect(Collectors.toList())
		);
	}

	private void addLabels(List<Long> labelIds, Long issueId) {
		issueRepository.addLabels(
			labelIds.stream()
				.map(labelId ->
					IssueLabel.builder()
						.issueId(issueId)
						.labelId(labelId)
						.build()
				)
				.collect(Collectors.toList())
		);
	}

	private void setMilestone(Long milestoneId, Long issueId) {
		issueRepository.setMilestone(issueId, milestoneId);
	}

	public IssueSearchResponse getIssues(IssueSearchParam issueSearchParam) {
		List<IssueSearchVo> issues = issueMapper.getIssues(issueSearchParam);
		Long labelCount = issueMapper.getLabelCount();
		Long milestoneCount = issueMapper.getMilestoneCount();
		String status = "open";
		if ((issueSearchParam != null) && (issueSearchParam.getStatus() != null)) {
			status = issueSearchParam.getStatus();
		}
		return IssueSearchResponse.from(IssueSearch.from(issues), labelCount, milestoneCount,
			status);
	}

	public IssueDetailResponse getIssueDetail(Long issueId) {
		if (!issueRepository.existsById(issueId)) {
			// todo: 커스텀 예외 생성 후 변경
			throw new RuntimeException("이슈를 찾을 수 없습니다.");
		}
		return IssueDetailResponse.from(issueMapper.getIssueDetail(issueId));
	}
}
