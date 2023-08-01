package codesquard.app.label.repository;

import java.util.List;
import java.util.Optional;

import codesquard.app.label.entity.Label;

public interface LabelRepository {

	Optional<Long> save(Label label);

	List<Label> findAll();

	Label findById(Long id);

	void updateBy(final Long labelId, final Label label);

	Long deleteById(Long id);
}
