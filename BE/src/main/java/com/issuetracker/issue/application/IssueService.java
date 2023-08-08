package com.issuetracker.issue.application;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.issuetracker.issue.application.dto.IssueCreateInformation;
import com.issuetracker.issue.application.dto.IssueCreateInputData;
import com.issuetracker.issue.application.dto.IssueDetailInformation;
import com.issuetracker.issue.application.dto.IssueSearchInformation;
import com.issuetracker.issue.application.dto.IssueSearchInputData;
import com.issuetracker.issue.application.dto.IssueUpdateData;
import com.issuetracker.issue.application.dto.IssuesCountInformation;
import com.issuetracker.issue.domain.AssignedLabelRepository;
import com.issuetracker.issue.domain.AssigneeRepository;
import com.issuetracker.issue.domain.Issue;
import com.issuetracker.issue.domain.IssueDetailRead;
import com.issuetracker.issue.domain.IssueMapper;
import com.issuetracker.issue.domain.IssueRepository;
import com.issuetracker.label.application.dto.LabelInformation;
import com.issuetracker.member.application.dto.MemberInformation;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class IssueService {

	private final IssueValidator issueValidator;
	private final IssueMapper issueMapper;
	private final IssueRepository issueRepository;
	private final AssigneeRepository assigneeRepository;
	private final AssignedLabelRepository assignedLabelRepository;

	@Transactional
	public IssueCreateInformation create(IssueCreateInputData issueCreateData) {
		issueValidator.verifyCreate(issueCreateData);

		Issue issue = issueCreateData.toIssue(LocalDateTime.now());
		Long savedId = issueRepository.save(issue);
		assigneeRepository.saveAll(issueCreateData.toAssignees(savedId));
		assignedLabelRepository.saveAll(issueCreateData.toAssignedLabel(savedId));
		return IssueCreateInformation.from(savedId);
	}

	public IssuesCountInformation findIssuesCount() {
		return IssuesCountInformation.from(issueRepository.findAllCount());
	}

	public List<IssueSearchInformation> search(IssueSearchInputData issueSearchInputData) {
		return IssueSearchInformation.from(issueMapper.search(issueSearchInputData.toIssueSearch()));
	}

	public List<MemberInformation> searchAssignee() {
		return MemberInformation.from(assigneeRepository.findAll());
	}

	public List<LabelInformation> searchAssignedLabel() {
		return LabelInformation.from(assignedLabelRepository.findAll());
	}

	public IssueDetailInformation findById(Long id) {
		IssueDetailRead issueDetailRead = issueMapper.findById(id);
		issueValidator.verifyIssueDetail(issueDetailRead);
		return IssueDetailInformation.from(issueDetailRead);
	}

	@Transactional
	public void updateOpen(IssueUpdateData issueUpdateData) {
		int updatedCount = issueRepository.updateOpen(issueUpdateData.getId(), issueUpdateData.getIsOpen());
		issueValidator.verifyUpdateIssue(updatedCount);
	}

	@Transactional
	public void updateTitle(IssueUpdateData issueUpdateData) {
		int updatedCount = issueRepository.updateTitle(issueUpdateData.getId(), issueUpdateData.getTitle());
		issueValidator.verifyUpdateIssue(updatedCount);
	}

	@Transactional
	public void updateContent(IssueUpdateData issueUpdateData) {
		int updatedCount = issueRepository.updateContent(issueUpdateData.getId(), issueUpdateData.getContent());
		issueValidator.verifyUpdateIssue(updatedCount);
	}
}
