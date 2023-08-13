package com.issuetrackermax.domain.issue;

import static com.issuetrackermax.domain.issue.IssueStatus.*;

import java.util.List;

import org.springframework.stereotype.Component;

import com.issuetrackermax.common.exception.ApiException;
import com.issuetrackermax.common.exception.domain.IssueException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class IssueValidator {
	private final IssueRepository issueRepository;

	public void existById(Long issueId) {
		if (!issueRepository.existById(issueId)) {
			throw new ApiException(IssueException.NOT_FOUND_ISSUE);
		}
	}

	public void existByIds(List<Long> ids) {
		if (!issueRepository.existByIds(ids)) {
			throw new ApiException(IssueException.NOT_FOUND_ISSUE);
		}
	}

	public void validStatus(String status) {
		if (!(status.equals(OPEN_ISSUE.status) || status.equals(CLOSED_ISSUE.status))) {
			throw new ApiException(IssueException.INVALID_ISSUE_STATUS);
		}
	}
}
