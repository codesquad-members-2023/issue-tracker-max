package com.issuetrackermax.service.assignee;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.issuetrackermax.common.exception.ApiException;
import com.issuetrackermax.common.exception.domain.AssigneeException;
import com.issuetrackermax.domain.assignee.AssigneeRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AssigneeService {
	private final AssigneeRepository assigneeRepository;

	@Transactional
	public void applyAssigneesToIssue(Long issueId, Long memberId) {
		int count = assigneeRepository.applyAssigneesToIssue(issueId, memberId);
		if (count != 1) {
			throw new ApiException(AssigneeException.NOT_FOUND_ASSIGNEE);
		}
	}

	public void deleteAppliedAssignees(Long issueId) {
		assigneeRepository.deleteAppliedAssignees(issueId);
	}

	@Transactional(readOnly = true)
	public Boolean existByIds(List<Long> ids) {
		return assigneeRepository.existByIds(ids);
	}
}
