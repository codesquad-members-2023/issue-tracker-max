package codesquard.app.label.repository;

import java.util.List;
import java.util.Optional;

import codesquard.app.label.entity.Label;

public interface LabelRepository {

	Optional<Long> save(final Label label);

	List<Label> findAll();

	void updateBy(final Long labelId, final Label label);

	void deleteBy(final Long labelId);
}
