package com.issuetracker.issue.application;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.issuetracker.issue.infrastrucure.AssigneeRepository;
import com.issuetracker.issue.application.dto.IssueCreateData;
import com.issuetracker.issue.application.dto.IssueCreateInformation;
import com.issuetracker.issue.application.dto.IssueSearchData;
import com.issuetracker.issue.application.dto.IssueSearchInformation;
import com.issuetracker.issue.domain.IssueMapper;
import com.issuetracker.issue.infrastrucure.IssueRepository;
import com.issuetracker.issue.infrastrucure.IssueLabelMappingRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class IssueService {

	private final IssueMapper issueMapper;
	private final IssueRepository issueRepository;
	private final AssigneeRepository assigneeRepository;
	private final IssueLabelMappingRepository labelRepository;

	@Transactional(readOnly = true)
	public List<IssueSearchInformation> search(IssueSearchData issueSearchData) {
		return IssueSearchInformation.from(issueMapper.search(issueSearchData));
	}

	public IssueCreateInformation create(IssueCreateData issueCreateData) {
		Long savedId = issueRepository.save(issueCreateData.toIssue());
		assigneeRepository.saveAll(issueCreateData.toAssignee(savedId));
		labelRepository.saveAll(issueCreateData.toIssueLabelMappings(savedId));
		return IssueCreateInformation.from(savedId);
	}
}
