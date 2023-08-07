package org.presents.issuetracker.issue.service;

import java.util.List;
import java.util.stream.Collectors;

import org.presents.issuetracker.issue.dto.request.IssueCreateRequestDto;
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
	public Long create(IssueCreateRequestDto issueCreateRequestDto) {
		Long savedIssueId = issueRepository.save(
			Issue.builder()
				.title(issueCreateRequestDto.getTitle())
				.authorId(issueCreateRequestDto.getAuthorId())
				.contents(issueCreateRequestDto.getContents())
				.build()
		);

		issueRepository.addAssignee(
			issueCreateRequestDto.getAssigneeIds().stream()
				.map(assigneeId ->
					Assignee.builder()
						.issueId(savedIssueId)
						.userId(assigneeId)
						.build()
				)
				.collect(Collectors.toList())
		);

		issueRepository.addLabel(
			issueCreateRequestDto.getLabelIds().stream()
				.map(labelId ->
					IssueLabel.builder()
						.issueId(savedIssueId)
						.labelId(labelId)
						.build()
				)
				.collect(Collectors.toList())
		);

		issueRepository.setMilestone(
			savedIssueId, issueCreateRequestDto.getMilestoneId()
		);

		return savedIssueId;
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
		return IssueDetailResponse.from(issueMapper.getIssueDetail(issueId));
	}
}
