package com.issuetracker.label.domain;

import java.util.List;

public interface LabelRepository {

	boolean existByIds(List<Long> labelIds);

	boolean existsByTitle(String title);

	Long save(Label label);

	int update(Label label);

	int delete(Label label);

	List<Label> findAll();

	List<Label> searchOrderByTitle();

	boolean existById(Long id);

	LabelCountMetadata calculateMetadata();
}
