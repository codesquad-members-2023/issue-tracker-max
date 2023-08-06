package codesquard.app.milestone.repository;

import java.util.List;
import java.util.Optional;

import codesquard.app.milestone.entity.Milestone;
import codesquard.app.milestone.entity.MilestoneStatus;

public interface MilestoneRepository {
	Optional<Long> save(final Milestone milestone);

	List<Milestone> findAllBy(final MilestoneStatus status);

	Long countIssuesBy(final MilestoneStatus status);

	Long countMilestonesBy(final MilestoneStatus status);

	Long countLabels();

	void updateBy(final Long milestoneId, final Milestone milestone);

	void updateBy(final Long milestoneId, final MilestoneStatus status);

	void deleteBy(final Long milestoneId);
}
