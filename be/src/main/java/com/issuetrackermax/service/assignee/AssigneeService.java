package com.issuetrackermax.service.assignee;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.issuetrackermax.domain.assignee.AssigneeRepository;
import com.issuetrackermax.domain.issue.IssueValidator;
import com.issuetrackermax.domain.member.MemberValidator;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AssigneeService {
	private final MemberValidator memberValidator;
	private final IssueValidator issueValidator;
	private final AssigneeRepository assigneeRepository;

	@Transactional
	public void applyAssigneesToIssue(Long issueId, Long memberId) {
		memberValidator.existById(memberId);
		issueValidator.existById(issueId);
		assigneeRepository.applyAssigneesToIssue(issueId, memberId);
	}

	@Transactional
	public void deleteAppliedAssignees(Long issueId) {
		assigneeRepository.deleteAppliedAssignees(issueId);
	}

}
