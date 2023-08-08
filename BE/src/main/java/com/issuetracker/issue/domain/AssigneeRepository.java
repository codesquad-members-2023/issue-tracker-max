package com.issuetracker.issue.domain;

import java.util.List;

import com.issuetracker.member.domain.Member;

public interface AssigneeRepository {

	int[] saveAll(List<Assignee> assignees);

	List<Member> findAll();

	List<Member> findAllAssignedToIssue(long issueId);

	List<Member> findAllUnassignedToIssue(long issueId);
}
