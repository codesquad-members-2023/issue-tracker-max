package com.issuetracker.issue.application;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.issuetracker.issue.application.dto.AuthorSearchInformation;
import com.issuetracker.issue.application.dto.IssueAssigneeInformation;
import com.issuetracker.issue.application.dto.IssueCreateInputData;
import com.issuetracker.issue.application.dto.IssueCreateInformation;
import com.issuetracker.issue.application.dto.IssueLabelMappingInformation;
import com.issuetracker.issue.application.dto.IssueSearchInputData;
import com.issuetracker.issue.application.dto.IssueSearchInformation;
import com.issuetracker.issue.domain.IssueMapper;
import com.issuetracker.issue.infrastrucure.AuthorRepository;
import com.issuetracker.issue.infrastrucure.AssigneeRepository;
import com.issuetracker.issue.infrastrucure.IssueLabelMappingRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class IssueService {

	private final IssueMapper issueMapper;
	private final IssueCreator issueCreator;
	private final AuthorRepository authorRepository;
	private final AssigneeRepository assigneeRepository;
	private final IssueLabelMappingRepository issueLabelMappingRepository;

	@Transactional(readOnly = true)
	public List<IssueSearchInformation> search(IssueSearchInputData issueSearchData) {
		return IssueSearchInformation.from(issueMapper.search(issueSearchData.toIssueSearch()));
	}

	public IssueCreateInformation create(IssueCreateInputData issueCreateData) {
		return IssueCreateInformation.from(issueCreator.create(issueCreateData));
	}
  
  @Transactional(readOnly = true)
	public List<AuthorSearchInformation> searchAuthors() {
		return AuthorSearchInformation.from(authorRepository.search());
  }
  
	@Transactional(readOnly = true)
	public List<IssueAssigneeInformation> findAllAssignee() {
		return IssueAssigneeInformation.from(assigneeRepository.findAll());
	}

	@Transactional(readOnly = true)
	public List<IssueLabelMappingInformation> findAllLabelMappings() {
		return IssueLabelMappingInformation.from(issueLabelMappingRepository.findAll());
	}
}
