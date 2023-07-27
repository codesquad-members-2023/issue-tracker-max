package com.issuetracker.app.issue.application;

import java.util.List;

import org.springframework.stereotype.Service;

import com.issuetracker.app.issue.application.dto.IssueSearchData;
import com.issuetracker.app.issue.application.dto.IssueSearchInformation;
import com.issuetracker.app.issue.domain.IssueMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IssueService {

	private final IssueMapper issueMapper;

	public List<IssueSearchInformation> search(IssueSearchData issueSearchData) {
		return IssueSearchInformation.from(issueMapper.search(issueSearchData));
	}
}
