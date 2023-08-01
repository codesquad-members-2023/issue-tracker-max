package org.presents.issuetracker.issue.service;

import java.util.List;
import java.util.stream.Collectors;

import org.presents.issuetracker.issue.dto.response.IssueListResponseDto;
import org.presents.issuetracker.issue.dto.response.IssueResponseDto;
import org.presents.issuetracker.issue.entity.Issue;
import org.presents.issuetracker.issue.mapper.IssueMapper;
import org.presents.issuetracker.label.entity.Label;
import org.presents.issuetracker.milestone.entity.Milestone;
import org.presents.issuetracker.user.entity.User;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IssueService {
	private final IssueMapper issueMapper;

	public IssueListResponseDto getIssueList() {
		List<Issue> issues = issueMapper.getIssueList();
		List<IssueResponseDto> issueResponseDtos = issues.stream()
			.map(issue -> {
				User user = issueMapper.getUser(issue.getAuthorId());
				List<Label> labels = issueMapper.getLabelsByIssueId(issue.getIssueId());
				Milestone milestone = issueMapper.getMilestoneByIssueId(issue.getIssueId());
				return IssueResponseDto.fromEntity(issue, user, labels, milestone);
			}).collect(Collectors.toList());
		Long openCount = issues.stream().filter(issue -> "open".equals(issue.getStatus())).count();
		Long closedCount = issues.stream().filter(issue -> "closed".equals(issue.getStatus())).count();

		return IssueListResponseDto.builder()
			.openIssueCount(openCount)
			.closedIssueCount(closedCount)
			.issues(issueResponseDtos)
			.build();
	}

}
