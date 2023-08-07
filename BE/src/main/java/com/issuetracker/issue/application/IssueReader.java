package com.issuetracker.issue.application;

import java.util.List;

import org.springframework.stereotype.Component;

import com.issuetracker.issue.application.dto.IssueSearchInputData;
import com.issuetracker.issue.domain.AssigneeRepository;
import com.issuetracker.issue.domain.Author;
import com.issuetracker.issue.domain.AuthorRepository;
import com.issuetracker.issue.domain.Issue;
import com.issuetracker.issue.domain.AssignedLabelRepository;
import com.issuetracker.issue.domain.IssueMapper;
import com.issuetracker.issue.domain.IssueRepository;
import com.issuetracker.issue.domain.IssuesCountData;
import com.issuetracker.label.domain.Label;
import com.issuetracker.member.domain.Member;
import com.issuetracker.milestone.domain.Milestone;
import com.issuetracker.milestone.domain.MilestoneRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class IssueReader {

	private final IssueMapper issueMapper;
	private final IssueRepository issueRepository;
	private final MilestoneRepository milestoneRepository;
	private final AuthorRepository authorRepository;
	private final AssigneeRepository assigneeRepository;
	private final AssignedLabelRepository assignedLabelRepository;

	public List<Issue> search(IssueSearchInputData issueSearchData) {
		return issueMapper.search(issueSearchData.toIssueSearch());
	}

	public IssuesCountData findIssuesCount() {
		return issueRepository.findAllCount();
	}

	public List<Milestone> searchMilestonesForFilter() {
		return milestoneRepository.findAllForFilter();
	}

	public List<Author> searchAuthors() {
		return authorRepository.search();
	}

	public List<Member> searchAssignee() {
		return assigneeRepository.findAll();
	}

	public List<Label> searchIssueLabelMapping() {
		return assignedLabelRepository.findAll();
	}
}
