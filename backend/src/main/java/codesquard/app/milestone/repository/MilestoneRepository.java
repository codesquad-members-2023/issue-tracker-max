package codesquard.app.milestone.repository;

import java.util.List;
import java.util.Optional;

import codesquard.app.milestone.entity.Milestone;

public interface MilestoneRepository {
	Optional<Long> save(final Milestone milestone);

	List<Milestone> findAll();

	Milestone findById(final Long id);

	void updateBy(final Long milestoneId, final Milestone milestone);

	Long deleteById(final Long id);
}
