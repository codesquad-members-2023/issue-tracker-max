package org.presents.issuetracker.issue.service;

import java.util.List;
import java.util.stream.Collectors;

import org.presents.issuetracker.issue.dto.IssueDto;
import org.presents.issuetracker.issue.dto.request.IssueCreateRequestDto;
import org.presents.issuetracker.issue.dto.response.IssueDetailResponse;
import org.presents.issuetracker.issue.entity.Assignee;
import org.presents.issuetracker.issue.entity.Issue;
import org.presents.issuetracker.issue.entity.IssueLabel;
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

	public List<IssueDto> getIssueList() {
		return issueMapper.getIssueList();
	}

	public IssueDetailResponse getIssueDetail(Long issueId) {
		return IssueDetailResponse.fromVo(issueMapper.getIssueDetail(issueId));
	}
}
