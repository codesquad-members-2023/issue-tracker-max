package com.issuetrackermax.service.issue;

import org.springframework.stereotype.Service;

import com.issuetrackermax.domain.issue.IssueRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class IssueService {
	private final IssueRepository issueRepository;
}
