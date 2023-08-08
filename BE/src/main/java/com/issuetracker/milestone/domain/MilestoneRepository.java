package com.issuetracker.milestone.domain;

import java.util.List;

public interface MilestoneRepository {

	boolean existById(Long id);

	List<Milestone> findAllForFilter();

	Long save(Milestone milestone);

	int update(Milestone milestone);

	List<Milestone> findAll();
}
