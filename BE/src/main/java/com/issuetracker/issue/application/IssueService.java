package com.issuetracker.issue.application;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.issuetracker.issue.application.dto.AuthorSearchInformation;
import com.issuetracker.issue.application.dto.IssueAssigneeInformation;
import com.issuetracker.issue.application.dto.IssueCreateInformation;
import com.issuetracker.issue.application.dto.IssueCreateInputData;
import com.issuetracker.issue.application.dto.IssueLabelMappingInformation;
import com.issuetracker.issue.application.dto.IssueSearchInformation;
import com.issuetracker.issue.application.dto.IssueSearchInputData;
import com.issuetracker.issue.application.dto.IssuesCountInformation;
import com.issuetracker.milestone.application.dto.MilestoneSearchInformation;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class IssueService {

	private final IssueCreator issueCreator;
	private final IssueReader issueReader;

	@Transactional
	public IssueCreateInformation create(IssueCreateInputData issueCreateData) {
		return IssueCreateInformation.from(issueCreator.create(issueCreateData));
	}

	public IssuesCountInformation findIssuesCount() {
		return IssuesCountInformation.from(issueReader.findIssuesCount());
	}

	public List<IssueSearchInformation> search(IssueSearchInputData issueSearchData) {
		return IssueSearchInformation.from(issueReader.search(issueSearchData));
	}

	public List<MilestoneSearchInformation> searchMilestonesForFilter() {
		return MilestoneSearchInformation.from(issueReader.searchMilestonesForFilter());
	}

	public List<AuthorSearchInformation> searchAuthors() {
		return AuthorSearchInformation.from(issueReader.searchAuthors());
  	}

	public List<IssueAssigneeInformation> searchAssignee() {
		return IssueAssigneeInformation.from(issueReader.searchAssignee());
	}

	public List<IssueLabelMappingInformation> searchIssueLabelMapping() {
		return IssueLabelMappingInformation.from(issueReader.searchIssueLabelMapping());
	}
}
