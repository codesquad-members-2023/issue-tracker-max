package codesquard.app.milestone.repository;

import java.util.List;

import codesquard.app.milestone.entity.Milestone;

public interface MilestoneRepository {
	Long save(Milestone milestone);

	List<Milestone> findAll();

	Milestone findById(Long id);

	Long modify(Milestone milestone);

	Long deleteById(Long id);
}
