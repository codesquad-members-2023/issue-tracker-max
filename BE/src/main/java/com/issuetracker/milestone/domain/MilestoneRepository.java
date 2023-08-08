package com.issuetracker.milestone.domain;

import java.util.List;

public interface MilestoneRepository {

	boolean existById(Long id);

	List<Milestone> findAllForFilter();

	Long save(Milestone milestone);

	int update(Milestone milestone);

	int updateOpenStatus(Milestone milestone);

	int delete(Milestone milestone);

	List<Milestone> findAll();
}
