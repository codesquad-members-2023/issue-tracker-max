package codesquard.app.label.repository;

import java.util.List;

import codesquard.app.label.entity.Label;

public interface LabelRepository {
	Long save(Label label);

	List<Label> findAll();

	Label findById(Long id);

	Long modify(Label label);

	Long deleteById(Long id);
}
