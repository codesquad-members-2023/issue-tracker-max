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
import com.issuetracker.issue.application.dto.IssueUpdateAllOpenData;
import com.issuetracker.issue.application.dto.IssueUpdateData;
import com.issuetracker.issue.application.dto.IssuesCountInformation;
import com.issuetracker.issue.domain.Issue;
import com.issuetracker.issue.domain.IssueDetailRead;
import com.issuetracker.issue.domain.IssueMapper;
import com.issuetracker.issue.domain.IssueRepository;
import com.issuetracker.issue.domain.assignedlabel.AssignedLabelRepository;
import com.issuetracker.issue.domain.assignee.AssigneeRepository;

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

	public IssueDetailInformation findById(Long id) {
		IssueDetailRead issueDetailRead = issueMapper.findById(id);
		issueValidator.verifyIssueDetail(issueDetailRead);
		return IssueDetailInformation.from(issueDetailRead);
	}

	@Transactional
	public void updateIssueOpen(IssueUpdateData issueUpdateData) {
		int updatedCount = issueRepository.updateOpen(issueUpdateData.getIsOpen(), issueUpdateData.getId());
		issueValidator.verifyUpdatedOrDeletedCount(updatedCount);
	}

	@Transactional
	public void updateIssueTitle(IssueUpdateData issueUpdateData) {
		issueValidator.verifyNonNull(issueUpdateData.getTitle());
		int updatedCount = issueRepository.updateTitle(issueUpdateData.getId(), issueUpdateData.getTitle());
		issueValidator.verifyUpdatedOrDeletedCount(updatedCount);
	}

	@Transactional
	public void updateIssueContent(IssueUpdateData issueUpdateData) {
		issueValidator.verifyNonNull(issueUpdateData.getContent());
		int updatedCount = issueRepository.updateContent(issueUpdateData.getId(), issueUpdateData.getContent());
		issueValidator.verifyUpdatedOrDeletedCount(updatedCount);
	}

	@Transactional
	public void deleteIssue(long id) {
		int deletedCount = issueRepository.delete(id);
		issueValidator.verifyUpdatedOrDeletedCount(deletedCount);
	}

	@Transactional
	public void updateIssueMilestone(IssueUpdateData issueUpdateData) {
		issueValidator.verifyUpdateMilestone(issueUpdateData.getMilestoneId());
		int updatedCount = issueRepository.updateMilestone(issueUpdateData.getId(), issueUpdateData.getMilestoneId());
		issueValidator.verifyUpdatedOrDeletedCount(updatedCount);
	}

	@Transactional
	public void updateAllIssueOpen(IssueUpdateAllOpenData issueUpdateAllOpenData) {
		List<Long> ids = issueUpdateAllOpenData.getIds();
		int updatedCount = issueRepository.updateAllOpen(issueUpdateAllOpenData.getIsOpen(), ids);
		issueValidator.verifyUpdatedOrDeletedCount(updatedCount, ids.size());
	}
}
