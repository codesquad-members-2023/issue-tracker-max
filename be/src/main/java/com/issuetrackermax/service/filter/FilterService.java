package com.issuetrackermax.service.filter;

import org.springframework.stereotype.Service;

import com.issuetrackermax.domain.issue.IssueRepository;
import com.issuetrackermax.domain.label.LabelRepository;
import com.issuetrackermax.domain.milestone.MilestoneRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class FilterService {
	private final MilestoneRepository milestoneRepository;
	private final IssueRepository issueRepository;
	private final LabelRepository labelRepository;

	public Long getMilestoneCount() {
		return milestoneRepository.getMilestoneCount();
	}

	public Long getLabelCount() {
		return labelRepository.getLabelCount();
	}

	public Long getOpenIssueCount() {
		return ((long)issueRepository.getOpenIssue().size());
	}

	public Long getClosedIssueCount() {
		return (long)issueRepository.getClosedIssue().size();

	}

}
