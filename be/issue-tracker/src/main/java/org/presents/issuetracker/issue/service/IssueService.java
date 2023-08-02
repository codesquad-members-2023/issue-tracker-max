package org.presents.issuetracker.issue.service;

import java.util.List;

import org.presents.issuetracker.issue.mapper.IssueMapper;
import org.presents.issuetracker.issue.dto.IssueDto;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IssueService {
	private final IssueMapper issueMapper;

	public List<IssueDto> getIssueList() {
		return issueMapper.getIssueList();
	}
}
