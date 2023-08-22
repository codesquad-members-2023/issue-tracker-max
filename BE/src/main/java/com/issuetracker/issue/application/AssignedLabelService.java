package com.issuetracker.issue.application;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.issuetracker.issue.application.dto.assignedlabel.AssignedLabelCandidatesInformation;
import com.issuetracker.issue.application.dto.assignedlabel.AssignedLabelCreateData;
import com.issuetracker.issue.application.dto.assignee.AssigneeCreateInformation;
import com.issuetracker.issue.domain.assignedlabel.AssignedLabelRepository;
import com.issuetracker.label.application.dto.LabelInformation;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AssignedLabelService {

	private final IssueValidator issueValidator;
	private final AssignedLabelRepository assignedLabelRepository;

	public List<LabelInformation> searchAssignedLabel() {
		return LabelInformation.from(assignedLabelRepository.findAll());
	}

	public AssignedLabelCandidatesInformation searchLabelCandidates(long issueId) {

		return AssignedLabelCandidatesInformation
			.from(
				assignedLabelRepository.findAllAssignedToIssue(issueId),
				assignedLabelRepository.findAllUnassignedToIssue(issueId)
			);
	}

	@Transactional
	public AssigneeCreateInformation createAssignedLabel(AssignedLabelCreateData assignedLabelCreateData) {
		issueValidator.verifyCreateAssignedLabel(assignedLabelCreateData);
		return AssigneeCreateInformation.from(assignedLabelRepository.save(assignedLabelCreateData.toAssignedLabel()));
	}

	@Transactional
	public int deleteAssignedLabel(long issueId, long labelId) {
		return assignedLabelRepository.delete(issueId, labelId);
	}

}
