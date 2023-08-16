package com.issuetracker.milestone.domain;

import java.util.List;

public interface MilestoneRepository {

	boolean existById(Long id);

	boolean existsByTitle(String title);

	List<Milestone> findAllForFilter();

	Long save(Milestone milestone);

	int update(Milestone milestone);

	int updateOpenStatus(Milestone milestone);

	int delete(Milestone milestone);

	List<Milestone> findAll(Milestone milestone);

	List<Milestone> findAllAssignedToIssue(long issueId);

	List<Milestone> findAllUnassignedToIssue(long issueId);

	MilestoneCountMetadata calculateMetadata();
}
