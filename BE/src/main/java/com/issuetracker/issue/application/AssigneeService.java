package com.issuetracker.issue.application;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.issuetracker.issue.application.dto.assignee.AssigneeCandidatesInformation;
import com.issuetracker.issue.application.dto.assignee.AssigneeCreateData;
import com.issuetracker.issue.application.dto.assignee.AssigneeCreateInformation;
import com.issuetracker.issue.domain.assignee.AssigneeRepository;
import com.issuetracker.member.application.dto.MemberInformation;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AssigneeService {

	private final IssueValidator issueValidator;
	private final AssigneeRepository assigneeRepository;

	public List<MemberInformation> searchAssignee() {
		return MemberInformation.from(assigneeRepository.findAll());
	}

	public AssigneeCandidatesInformation searchAssigneeCandidates(long issueId) {

		return AssigneeCandidatesInformation
			.from(
				assigneeRepository.findAllAssignedToIssue(issueId),
				assigneeRepository.findAllUnassignedToIssue(issueId)
			);
	}

	@Transactional
	public AssigneeCreateInformation createAssignee(AssigneeCreateData assigneeCreateData) {
		issueValidator.verifyCreateAssignee(assigneeCreateData);
		return AssigneeCreateInformation.from(assigneeRepository.save(assigneeCreateData.toAssignee()));
	}

	@Transactional
	public int deleteAssignee(Long assigneeId) {
		return assigneeRepository.delete(assigneeId);
	}
}
